package com.bmh.letsgogeonative.ui.login

import android.content.Intent
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.bmh.letsgogeonative.databinding.SignUpBinding
import com.bmh.letsgogeonative.utils.auth.AuthManager
import com.bmh.letsgogeonative.utils.util.UtilsInterface
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.runBlocking

class SignUpActivity : AppCompatActivity(), View.OnClickListener, UtilsInterface,
    TextInputLayout.OnEditTextAttachedListener {
    private lateinit var binding: SignUpBinding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignUpBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.root)

        // Handle Button Click
        binding.btnSignIn.setOnClickListener(this)
        binding.btnSignUp.setOnClickListener(this)
        binding.signUpLayout.setOnClickListener(this)

        // Handle Form
        binding.suEmail.addOnEditTextAttachedListener(this)
        binding.suPassword.addOnEditTextAttachedListener(this)
        binding.suConfirmPassword.addOnEditTextAttachedListener(this)

        (binding.imageView3.drawable as AnimatedImageDrawable).start()
    }

    override fun onClick(btn: View) {
        when (btn.id) {
            binding.btnSignIn.id -> navigateToSignUp()
            binding.btnSignUp.id -> {
                binding.btnSignUp.isEnabled = false
                hideKeyboard(this, binding.root.findFocus())
                runBlocking {
                    createUser()
                }
            }

            binding.signUpLayout.id -> {
                hideKeyboard(this, currentFocus)
            }
        }
    }

    /**
     * Register User
     * False = Failed
     * True = Success
     */
    private fun createUser() {
        if (
            binding.suPassword.editText?.text.toString() == binding.suConfirmPassword.editText?.text.toString() &&
            binding.suEmail.editText!!.text.isNotEmpty()
        ) {
            binding.progressBarSignup.isVisible = true
            AuthManager(this)
                .createUser(
                    email = binding.suEmail.editText?.text.toString(),
                    password = binding.suPassword.editText?.text.toString(),
                    onSuccess = { onSuccess() },
                    onFailed = { onFailed() }
                )
        } else {
            Snackbar.make(binding.root, "Password Is Not Same", Snackbar.LENGTH_SHORT).show()
            binding.progressBarSignup.isVisible = false
            binding.btnSignUp.isEnabled = true
        }
    }

    /***
     * Handle When Registration Success
     */
    private fun onSuccess() {
        Snackbar.make(binding.root, "Success", Snackbar.LENGTH_SHORT).show()
        binding.progressBarSignup.isVisible = false
        binding.btnSignUp.isEnabled = true
    }

    /***
     * Handle When Registration Failed
     */
    private fun onFailed() {
        Snackbar.make(binding.root, "Failed", Snackbar.LENGTH_SHORT).show()
        binding.progressBarSignup.isVisible = false
        binding.btnSignUp.isEnabled = true
    }

    /***
     * Navigate To Sign Up Page
     */
    private fun navigateToSignUp() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    /*
     * TextField Input Handler
     */
    override fun onEditTextAttached(textInputLayout: TextInputLayout) {
        when (textInputLayout.id) {
            binding.suEmail.id -> {
                textInputLayout.editText?.doOnTextChanged { textInput, _, _, _ ->
                }
            }

            binding.suPassword.id -> {
                textInputLayout.editText?.doOnTextChanged { textInput, _, _, _ ->
                }
            }

            binding.suConfirmPassword.id -> {
                textInputLayout.editText?.doOnTextChanged { textInput, _, _, _ ->
                }
            }
        }
    }

}