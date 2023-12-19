package com.bmh.letsgogeonative.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bmh.letsgogeonative.model.Constant
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginViewModel : ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth

    private val _currentUser = MutableLiveData<Constant.User>()

    val currentUser: LiveData<Constant.User> = _currentUser

    var email = ""
    init {
        _currentUser.value?.email = auth.currentUser?.email.toString()
        email = auth.currentUser?.email.toString()
    }

    fun setProfileImage(image: String) {
        _currentUser.value?.image = image;
    }
}