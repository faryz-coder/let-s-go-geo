package com.bmh.letsgogeonative.ui.list_topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmh.letsgogeonative.databinding.FragmentListTopicBinding

class ListTopicFragment : Fragment() {
    private var _binding: FragmentListTopicBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListTopicBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sections = mutableListOf<Sections>()
        for (i in 0..3) {
            sections.add(Sections("Topic 1", "", ""))
        }

        binding.listTopicRecyclerview.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = TopicAdapter(sections)
        }

        return root
    }
}