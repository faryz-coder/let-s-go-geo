package com.bmh.letsgogeonative.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.bmh.letsgogeonative.databinding.FragmentUserProfileBinding
import com.bmh.letsgogeonative.utils.firestore.FirestoreManager
import com.bmh.letsgogeonative.utils.util.UtilsInterface
import com.google.android.material.textfield.TextInputLayout

class UserProfile : Fragment(), TextInputLayout.OnEditTextAttachedListener, View.OnClickListener,
    UtilsInterface {
    private var _binding: FragmentUserProfileBinding? = null
    val firestoreManager = FirestoreManager()

    private val binding get() = _binding!!
    private var _name = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestoreManager.userProfile(::displayProfile)

        binding.profileName.addOnEditTextAttachedListener(this)

        binding.btnSave.setOnClickListener(this)
        binding.userProfileLayout.setOnClickListener(this)
    }

    private fun displayProfile(email: String, name: String) {
        binding.profileEmail.editText?.setText(email)
        binding.profileName.editText?.setText(name)
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
            }
            binding.userProfileLayout.id -> {
                hideKeyboard(requireActivity(), requireView().findFocus())
                binding.profileName.editText?.clearFocus()
            }
        }
    }
}