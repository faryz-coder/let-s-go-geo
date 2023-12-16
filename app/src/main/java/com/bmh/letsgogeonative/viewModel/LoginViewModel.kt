package com.bmh.letsgogeonative.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class LoginViewModel : ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth

    private val _currentUser = MutableLiveData<FirebaseUser>().apply {
        value = auth.currentUser
    }

    val currentUser: LiveData<FirebaseUser> = _currentUser

}