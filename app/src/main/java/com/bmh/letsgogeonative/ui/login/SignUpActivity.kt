package com.bmh.letsgogeonative.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle Button Click
        binding.btnSignIn.setOnClickListener(this)
        binding.btnSignUp.setOnClickListener(this)
        binding.signUpLayout.setOnClickListener(this)

        // Handle Form
        binding.suEmail.addOnEditTextAttachedListener(this)
        binding.suPassword.addOnEditTextAttachedListener(this)
        binding.suConfirmPassword.addOnEditTextAttachedListener(this)
    }

    override fun onClick(btn: View) {
        when (btn.id) {
            binding.btnSignIn.id -> navigateToSignUp()
            binding.btnSignUp.id -> {
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
        AuthManager(this)
            .createUser(
                email = binding.suEmail.editText?.text.toString(),
                password = binding.suPassword.editText?.text.toString(),
                onSuccess = { onSuccess() },
                onFailed = { onFailed() }
            )
    }

    /***
     * Handle When Registration Success
     */
    private fun onSuccess() {
        Snackbar.make(binding.root, "Success", Snackbar.LENGTH_SHORT).show()
    }

    /***
     * Handle When Registration Failed
     */
    private fun onFailed() {
        Snackbar.make(binding.root, "Failed", Snackbar.LENGTH_SHORT).show()
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