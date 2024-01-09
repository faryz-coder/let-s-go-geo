package com.bmh.letsgogeonative.ui.admin.uploadsScreen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bmh.letsgogeonative.R
import com.bmh.letsgogeonative.databinding.FragmentUploadBinding
import com.bmh.letsgogeonative.model.Question
import com.bmh.letsgogeonative.utils.firestore.FirestoreManager
import com.bmh.letsgogeonative.utils.util.UtilsInterface
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

class UploadFragment : Fragment(), UtilsInterface, View.OnClickListener {
    private var _binding: FragmentUploadBinding? = null

    private val binding get() = _binding!!
    private var questions: Question.SetQuestion? = null
    private var selectedSection = ""

    private val pickFile =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    data.data?.let { handleSelectedFile(it) }
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)

        binding.sectionSelection.editText?.doOnTextChanged { selectedText, _, _, _ ->
            Log.d("LearningMaterial", "selectedText: $selectedText")
            if (selectedText != null) {
                selectedSection = if (selectedText.contains("Lower")) {
                    "lower"

                } else {
                    "upper"

                }
            }
        }

        binding.fileSelection.setOnClickListener(this)
        binding.submitQuestion.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(btn: View) {
        when (btn.id) {
            binding.fileSelection.id -> {
                pickFile()
            }

            binding.submitQuestion.id -> {
                if (questions != null && binding.textInputLayout.editText!!.text.isNotEmpty() && binding.notesLink.editText!!.text.isNotEmpty() && selectedSection.isNotEmpty()) {
                    FirestoreManager().uploadQuestion(
                        section = selectedSection,
                        title = binding.textInputLayout.editText!!.text.toString(),
                        notes = binding.notesLink.editText!!.text.toString(),
                        questions = questions!!,
                        onSuccess = {
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                        },
                        onFailed = {
                            Toast.makeText(requireContext(), "Failed", Toast.LENGTH_LONG).show()
                        }
                    )
                }
            }
        }
    }

    private fun pickFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/json"
        }
        pickFile.launch(intent)
    }

    private fun handleSelectedFile(uri: Uri) {

        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val fileName = getFileName(uri)
        binding.fileSelection.text = fileName

        inputStream?.use { stream ->
            val reader = BufferedReader(InputStreamReader(stream))
            val stringBuilder = StringBuilder()
            var line: String?

            try {
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line).append('\n')
                }
                val jsonString = stringBuilder.toString()
                binding.fileContent.text = jsonString
                val gson = Gson()
                questions = gson.fromJson(jsonString, Question.SetQuestion::class.java)
                val size = questions!!.questions.size
                Toast.makeText(requireContext(), "Size :$size", Toast.LENGTH_LONG).show()

            } catch (e: Exception) {
                e.printStackTrace()
                // Handle exceptions as needed
            }
        }
    }

    private fun getFileName(uri: Uri): String {
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null, null)
        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            it.moveToFirst()
            return it.getString(nameIndex)
        }
        return ""
    }
}