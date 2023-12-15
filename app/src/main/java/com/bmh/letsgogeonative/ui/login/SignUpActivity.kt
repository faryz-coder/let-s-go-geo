package com.bmh.letsgogeonative.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bmh.letsgogeonative.databinding.SignUpBinding

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: SignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener(this)
    }

    override fun onClick(btn: View) {
        when (btn.id) {
            binding.btnSignIn.id -> navigateToSignUp()
        }
    }

    private fun navigateToSignUp() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }
}