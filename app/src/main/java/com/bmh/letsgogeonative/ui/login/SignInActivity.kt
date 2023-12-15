package com.bmh.letsgogeonative.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bmh.letsgogeonative.databinding.SignInBinding

class SignInActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: SignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener(this)
    }

    override fun onClick(btn: View) {
        when(btn.id) {
            binding.btnSignup.id -> navigateToSignUp()
        }
    }

    private fun navigateToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}