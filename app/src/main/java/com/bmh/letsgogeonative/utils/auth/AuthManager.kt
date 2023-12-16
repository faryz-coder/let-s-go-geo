package com.bmh.letsgogeonative.utils.auth

import android.app.Activity
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class AuthManager(activity: Activity) {

    private var auth: FirebaseAuth = Firebase.auth
    private var activity: Activity

    init {
        this.activity = activity
        Log.d("Faris", "Auth Initiated")
    }

    fun createUser(email: String, password: String, onSuccess: () -> Unit, onFailed: () -> Unit) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d("Faris::AuthManager", "createUserWithEmail:success")
                    onSuccess.invoke()
                } else {
                    onFailed.invoke()
                    Log.w("Faris::AuthManager", "createUserWithEmail:failed", task.exception)
                }
            }
    }

    fun signInUser(email: String, password: String, onSuccess: () -> Unit, onFailed: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onFailed.invoke()
                }
            }
    }
}