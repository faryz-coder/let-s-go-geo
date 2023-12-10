package com.bmh.letsgogeonative.ui.list_topic.topic_content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bmh.letsgogeonative.databinding.FragmentTopicContentBinding


class TopicContentFragment : Fragment() {
    private var _binding: FragmentTopicContentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopicContentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
}