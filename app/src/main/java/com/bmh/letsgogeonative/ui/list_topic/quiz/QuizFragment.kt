package com.bmh.letsgogeonative.ui.list_topic.quiz

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.bmh.letsgogeonative.databinding.FragmentQuizBinding

class QuizFragment: Fragment(), RadioGroup.OnCheckedChangeListener {
    private var _binding: FragmentQuizBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.radioGroup.setOnCheckedChangeListener(this)

        return root
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        d("Faris", "RadioButtonSelected: $p1")
        when (p1) {
            binding.anwerA.id -> d("Faris", "RadioButtonSelected: A")
            binding.answerB.id -> d("Faris", "RadioButtonSelected: B")
            binding.answerC.id -> d("Faris", "RadioButtonSelected: C")
        }
    }
}