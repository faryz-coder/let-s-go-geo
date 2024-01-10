package com.bmh.letsgogeonative.ui.admin.listTopic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmh.letsgogeonative.R
import com.bmh.letsgogeonative.databinding.FragmentAdminListTopicBinding
import com.bmh.letsgogeonative.helper.OnSelection
import com.bmh.letsgogeonative.ui.list_topic.ListTopicViewModel
import com.bmh.letsgogeonative.ui.list_topic.Sections
import com.bmh.letsgogeonative.utils.firestore.FirestoreManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AdminListTopic: Fragment(), OnSelection {
    private var _binding: FragmentAdminListTopicBinding? = null

    private val binding get() = _binding!!
    private var selectedSection = ""
    private lateinit var topicAdapter: AdminTopicAdapter
    private lateinit var listTopicViewModel: ListTopicViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminListTopicBinding.inflate(inflater, container, false)
        listTopicViewModel = ViewModelProvider(requireActivity())[ListTopicViewModel::class.java]

        binding.sectionSelection.editText?.doOnTextChanged { selectedText, _, _, _ ->
            Log.d("LearningMaterial", "selectedText: $selectedText")
            if (selectedText != null) {
                selectedSection = if (selectedText.contains("Lower")) {
                    "lower"
                } else {
                    "upper"
                }
                FirestoreManager().getListTopic(selectedSection, listTopicViewModel)
            }
        }

        val sections = mutableListOf<Sections>()
        topicAdapter = AdminTopicAdapter(sections, ::onSelection)
        binding.adminListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = topicAdapter
        }

        listTopicViewModel.section.observe(viewLifecycleOwner) {
            sections.clear()
            sections.addAll(it)
            topicAdapter.notifyDataSetChanged()
            Log.d("Malar", "AdminlistTopicViewModel :: $it")
        }

        return binding.root
    }

    override fun onSelection(selected: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Confirm to delete?")
            .setIcon(R.drawable.wrong)
            .setNegativeButton("Cancel") { dialog, which ->
                // Respond to negative button press
            }
            .setPositiveButton("Confirm") { dialog, which ->
                // Respond to positive button press
                FirestoreManager().removeTopic(selectedSection, selected, ::onSuccess)
            }
            .show()
    }

    private fun onSuccess() {
        FirestoreManager().getListTopic(selectedSection, listTopicViewModel)
        Toast.makeText(requireContext(), "Deleted!", Toast.LENGTH_SHORT).show()
    }
}