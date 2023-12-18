package com.bmh.letsgogeonative.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bmh.letsgogeonative.databinding.FragmentSlideshowBinding
import com.bmh.letsgogeonative.viewModel.LoginViewModel
import kotlin.math.log

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private var loginViewModel: LoginViewModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        loginViewModel!!.currentUser.observe(viewLifecycleOwner) {
            binding.textView6.text = it.email
        }

//        val textView: TextView = binding.textSlideshow
//        slideshowViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}