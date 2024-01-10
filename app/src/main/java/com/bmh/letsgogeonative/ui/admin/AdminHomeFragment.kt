package com.bmh.letsgogeonative.ui.admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bmh.letsgogeonative.R
import com.bmh.letsgogeonative.SplashActivity
import com.bmh.letsgogeonative.databinding.FragmentAdminHomeBinding
import com.bmh.letsgogeonative.utils.auth.AuthManager

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

        return root
    }

    override fun onClick(btn: View) {
        when (btn.id) {
            binding.adminLogout.id -> {
                AuthManager(requireActivity()).logout()
                val intent = Intent(requireActivity(), SplashActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            binding.upperList.id -> {
                findNavController().navigate(R.id.adminListTopic)
            }
            binding.upperUpload.id -> {
                findNavController().navigate(R.id.uploadFragment)
            }
        }
    }
}