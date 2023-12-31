package com.bmh.letsgogeonative.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bmh.letsgogeonative.databinding.FragmentAdminHomeBinding

class AdminHomeFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentAdminHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.adminLogout.setOnClickListener(this)
        binding.upperList.setOnClickListener(this)
        binding.upperUpload.setOnClickListener(this)
        binding.lowerList.setOnClickListener(this)
        binding.lowerUpload.setOnClickListener(this)

        return root
    }

    override fun onClick(btn: View) {
        when (btn.id) {
            binding.adminLogout.id -> {}
            binding.upperList.id -> {}
            binding.upperUpload.id -> {}
            binding.lowerList.id -> {}
            binding.lowerUpload.id -> {}
        }
    }
}