package com.bmh.letsgogeonative.ui.slideshow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bmh.letsgogeonative.databinding.FragmentSlideshowBinding
import com.bmh.letsgogeonative.model.Constant
import com.bmh.letsgogeonative.utils.firestore.FirestoreManager
import com.bmh.letsgogeonative.viewModel.LoginViewModel
import com.squareup.picasso.Picasso

class PerformanceFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var scoreAdater: ScoreAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val score = mutableListOf<Constant.Score>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Log.d("SlideshowFragment", "email: ${loginViewModel.email}")
        binding.textView6.text = loginViewModel.email

//        val textView: TextView = binding.textSlideshow
//        slideshowViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        scoreAdater = ScoreAdapter(score)

        binding.scoreRecyclerView.apply {
            layoutManager = GridLayoutManager(this.context, 4)
            adapter = scoreAdater
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirestoreManager().userProfile(::displayProfile)

        binding.sectionsSelection.editText?.doOnTextChanged { selectedText, _, _, _ ->
            Log.d("LearningMaterial", "selectedText: $selectedText")
            if (selectedText != null) {
                FirestoreManager().getResult(
                    if (selectedText.contains("Lower")) {
                        "lower"
                    } else {
                        "upper"
                    },
                    ::showResult
                )
            }

        }
    }

    private fun showResult(score: (MutableList<Constant.Score>)) {
        this.score.clear()
        this.score.addAll(score)
        scoreAdater.notifyDataSetChanged()
        binding.textView8.isVisible = true
    }

    private fun displayProfile(email: String, name: String, image: String) {
        binding.textView6.text = email
        if (image.isNotEmpty()) {
            Picasso.get().load(image).into(binding.imageProfile)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}