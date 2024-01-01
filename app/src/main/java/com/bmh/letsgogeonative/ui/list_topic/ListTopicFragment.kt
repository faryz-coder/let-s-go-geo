package com.bmh.letsgogeonative.ui.list_topic

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmh.letsgogeonative.R
import com.bmh.letsgogeonative.databinding.FragmentListTopicBinding
import com.bmh.letsgogeonative.helper.OnSelection
import com.bmh.letsgogeonative.utils.firestore.FirestoreManager

class ListTopicFragment : Fragment(), OnSelection {
    private var _binding: FragmentListTopicBinding? = null
    private lateinit var listTopicViewModel: ListTopicViewModel
    private lateinit var topicAdapter: TopicAdapter
    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListTopicBinding.inflate(inflater, container, false)
        val root: View = binding.root

        listTopicViewModel = ViewModelProvider(requireActivity())[ListTopicViewModel::class.java]

        val sections = mutableListOf<Sections>()

        topicAdapter = TopicAdapter(sections, ::onSelection)

        binding.listTopicRecyclerview.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = topicAdapter
        }

        FirestoreManager().getListTopic(listTopicViewModel.sectionSelected, listTopicViewModel)

        listTopicViewModel.section.observe(viewLifecycleOwner) {
            sections.clear()
            sections.addAll(it)
            topicAdapter.notifyDataSetChanged()
            Log.d("Malar", "listTopicViewModel :: $it")
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onSelection(selected: String) {
        listTopicViewModel.topicSelected = selected
        findNavController().navigate(R.id.action_listTopicFragment_to_topicContentFragment)
    }
}