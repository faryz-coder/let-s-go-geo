package com.bmh.letsgogeonative.ui.list_topic.topic_content

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bmh.letsgogeonative.R
import com.bmh.letsgogeonative.databinding.FragmentTopicContentBinding
import com.bmh.letsgogeonative.ui.list_topic.ListTopicViewModel
import com.bmh.letsgogeonative.utils.firestore.FirestoreManager


class TopicContentFragment : Fragment() {
    private var _binding: FragmentTopicContentBinding? = null
    private lateinit var listTopicViewModel: ListTopicViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopicContentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        listTopicViewModel = ViewModelProvider(requireActivity())[ListTopicViewModel::class.java]

        val mediaController = MediaController(requireContext())
        mediaController.setMediaPlayer(binding.videoView)
        binding.videoView.setMediaController(mediaController)

        listTopicViewModel.topicContent.observe(viewLifecycleOwner) {
            Log.d("Faris", "topicContent:: $it , ${it.size}")
//            Picasso.get().load(it[0].noteUrl).into(binding.imageView3)
            binding.videoView.setVideoURI(Uri.parse(it[0].noteUrl))
            binding.videoView.start()
        }
        FirestoreManager().getTopicContent(listTopicViewModel)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_topicContentFragment_to_quizFragment)
        }
    }
}