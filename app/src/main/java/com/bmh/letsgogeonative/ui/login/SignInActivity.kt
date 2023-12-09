package com.bmh.letsgogeonative.ui.login

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.bmh.letsgogeonative.databinding.SignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: SignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}