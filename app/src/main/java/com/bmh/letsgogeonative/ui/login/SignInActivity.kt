package com.bmh.letsgogeonative.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.bmh.letsgogeonative.MainActivity
import com.bmh.letsgogeonative.databinding.SignInBinding
import com.bmh.letsgogeonative.utils.auth.AuthManager
import com.bmh.letsgogeonative.utils.util.UtilsInterface
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignInActivity : AppCompatActivity(), View.OnClickListener, UtilsInterface,
    TextInputLayout.OnEditTextAttachedListener {
    private lateinit var binding: SignInBinding
    private var auth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle Button Click
        binding.btnSignup.setOnClickListener(this)
        binding.btnSignin.setOnClickListener(this)

        // Handle Form
        binding.inputUsername.addOnEditTextAttachedListener(this)
        binding.inputPassword.addOnEditTextAttachedListener(this)
    }

    override fun onResume() {
        super.onResume()
        // Check If User Already Sign In
        // Navigate to Main if true
        if (auth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onClick(btn: View) {
        when (btn.id) {
            binding.btnSignup.id -> navigateToSignUp()
            binding.btnSignin.id -> initSignIn()
        }
    }

    /**
     * Sign In User
     * False = Failed
     * True = Success
     */
    private fun initSignIn() {
        AuthManager(this)
            .signInUser(
                email = binding.inputUsername.editText?.text.toString(),
                password = binding.inputPassword.editText?.text.toString(),
                onSuccess = { onSuccess() },
                onFailed = { onFailed() }
            )
    }

    /***
     * Handle When Registration Success
     */
    private fun onSuccess() {
        Snackbar.make(binding.root, "Success", Snackbar.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /***
     * Handle When Registration Failed
     */
    private fun onFailed() {
        Snackbar.make(binding.root, "Failed", Snackbar.LENGTH_SHORT).show()
    }

    private fun navigateToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    override fun onEditTextAttached(textInputLayout: TextInputLayout) {
        when (textInputLayout.id) {
            binding.inputUsername.id -> {
                textInputLayout.editText?.doOnTextChanged { textInput, _, _, _ ->
                }
            }

            binding.inputPassword.id -> {
                textInputLayout.editText?.doOnTextChanged { textInput, _, _, _ ->
                }
            }
        }
    }
}