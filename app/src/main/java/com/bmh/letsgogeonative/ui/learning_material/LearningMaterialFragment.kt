package com.bmh.letsgogeonative.ui.learning_material

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bmh.letsgogeonative.R
import com.bmh.letsgogeonative.databinding.FragmentLearningMaterialBinding
import com.bmh.letsgogeonative.ui.list_topic.ListTopicViewModel

class LearningMaterialFragment : Fragment() {

    private var _binding: FragmentLearningMaterialBinding? = null
    private lateinit var listTopicViewModel: ListTopicViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLearningMaterialBinding.inflate(inflater, container, false)
        listTopicViewModel = ViewModelProvider(requireActivity())[ListTopicViewModel::class.java]

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sectionsSelection.editText?.doOnTextChanged { selectedText, _, _, _ ->
            d("LearningMaterial", "selectedText: $selectedText")
            if (selectedText != null) {
                if (selectedText.contains("Lower")) {
                    listTopicViewModel.sectionSelected = "lower"

                } else {
                    listTopicViewModel.sectionSelected = "upper"

                }
            }
            findNavController().navigate(R.id.action_nav_gallery_to_listTopicFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}