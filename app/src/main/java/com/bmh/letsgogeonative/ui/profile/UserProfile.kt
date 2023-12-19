package com.bmh.letsgogeonative.ui.profile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.bmh.letsgogeonative.databinding.FragmentUserProfileBinding
import com.bmh.letsgogeonative.utils.firestore.FirestoreManager
import com.bmh.letsgogeonative.utils.storage.StorageManager
import com.bmh.letsgogeonative.utils.util.UtilsInterface
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso

class UserProfile : Fragment(), TextInputLayout.OnEditTextAttachedListener, View.OnClickListener,
    UtilsInterface {
    private var _binding: FragmentUserProfileBinding? = null
    val firestoreManager = FirestoreManager()

    private val binding get() = _binding!!
    private var _name = ""
    private var updateImg : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)

        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                binding.profileImage.setImageURI(uri)
                updateImg = uri
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

        binding.btnProfileImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        return binding.root
    }

    private fun onSuccess(downloadUrl: String) {
        FirestoreManager().updateProfileImage(downloadUrl)
        binding.btnSave.isEnabled = true
    }

    private fun onFailed() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestoreManager.userProfile(::displayProfile)

        binding.profileName.addOnEditTextAttachedListener(this)

        binding.btnSave.setOnClickListener(this)
        binding.userProfileLayout.setOnClickListener(this)
    }

    private fun displayProfile(email: String, name: String, image: String) {
        binding.profileEmail.editText?.setText(email)
        binding.profileName.editText?.setText(name)
        if (!image.isNullOrEmpty()) {
            Picasso.get().load(image).into(
                binding.profileImage
            )
        }
        _name = name
    }

    override fun onEditTextAttached(textInputLayout: TextInputLayout) {
        when (textInputLayout.id) {
            binding.profileName.id -> {
                textInputLayout.editText?.doOnTextChanged { textInput, _, _, _ ->
                    binding.btnSave.isEnabled = !(_name == textInput || textInput.isNullOrEmpty())
                }
            }
        }
    }

    override fun onClick(btn: View) {
        when (btn.id) {
            binding.btnSave.id -> {
                binding.btnSave.isEnabled = false
                firestoreManager.updateProfile(binding.profileName.editText?.text.toString())
                if (updateImg != null) {
                    StorageManager().uploadImg(
                        updateImg!!,
                        onSuccess = ::onSuccess,
                        onFailed = {},
                    )
                }
            }
            binding.userProfileLayout.id -> {
                hideKeyboard(requireActivity(), requireView().findFocus())
                binding.profileName.editText?.clearFocus()
            }
        }
    }
}