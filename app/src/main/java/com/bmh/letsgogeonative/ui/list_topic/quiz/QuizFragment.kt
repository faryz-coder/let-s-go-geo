package com.bmh.letsgogeonative.ui.list_topic.quiz

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bmh.letsgogeonative.R
import com.bmh.letsgogeonative.databinding.FragmentQuizBinding
import com.bmh.letsgogeonative.model.Constant
import com.bmh.letsgogeonative.ui.list_topic.ListTopicViewModel
import com.bmh.letsgogeonative.utils.firestore.FirestoreManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizFragment : Fragment(), RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private var _binding: FragmentQuizBinding? = null
    private lateinit var listTopicViewModel: ListTopicViewModel

    private val binding get() = _binding!!
    private var index = 0
    private var total = 0
    private lateinit var question: Constant.Question
    private var selectedAnswer = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        val root: View = binding.root
        listTopicViewModel = ViewModelProvider(requireActivity())[ListTopicViewModel::class.java]

        binding.radioGroup.setOnCheckedChangeListener(this)
        binding.btnContinueOrComplete.setOnClickListener(this)

        FirestoreManager().getQuestion(listTopicViewModel)

        listTopicViewModel.setsQuestion.observe(viewLifecycleOwner) {
            d("QuizFragment", "$it")
            question = it
            nextQuestion()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        d("Faris", "RadioButtonSelected: $p1")
        when (p1) {
            binding.answerA.id -> selectedAnswer = 0
            binding.answerB.id -> selectedAnswer = 1
            binding.answerC.id -> selectedAnswer = 2
            binding.answerD.id -> selectedAnswer = 3
        }
    }

    override fun onClick(btn: View) {
        when (btn.id) {
            binding.btnContinueOrComplete.id -> {
                binding.btnContinueOrComplete.isEnabled = false
               checkAnswer()
            }
        }
    }

    private fun nextQuestion() {
        binding.quicNo.text = "${index + 1}"
        binding.quizQuestion.text = question.question[index]
        binding.answerA.text = question.option[index][0]
        binding.answerB.text = question.option[index][1]
        binding.answerC.text = question.option[index][2]
        binding.answerD.text = question.option[index][3]

        //default checked
        binding.answerA.isChecked = true

        total = question.question.size

        binding.overlayResult.isGone = true
        binding.btnContinueOrComplete.isEnabled = true
    }

    private fun checkAnswer() {
        if (selectedAnswer == question.answer[index]) {
            d("QuizFragment", "Correct")
            listTopicViewModel.marks += 1
            showChoiceResult(true)
            proceed()
        } else {
            d("QuizFragment", "Wrong")
            showChoiceResult(false)
            proceed()
        }
    }

    private fun proceed() {
        // Proceed to Next Question OR End The Quiz
        if (index + 1 == total) {
            d("QuizFragment", "Complete")
            FirestoreManager().submitResult(listTopicViewModel)
            lifecycleScope.launch {
                delay(1000L)
                findNavController().popBackStack(R.id.nav_gallery, false)
            }
        } else {
            index += 1
            nextQuestion()
        }
    }

    private fun showChoiceResult(result: Boolean) {
        binding.overlayResult.isGone = false
        if (result) {
            binding.imgResult.setImageResource(R.drawable.rounded_done_24)
        } else {
            binding.imgResult.setImageResource(R.drawable.rounded_close_24)
        }
    }
}