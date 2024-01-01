package com.bmh.letsgogeonative.utils.auth

import android.app.Activity
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class AuthManager(activity: Activity) {

    private var auth: FirebaseAuth = Firebase.auth
    private val db: FirebaseFirestore = Firebase.firestore
    private var activity: Activity

    init {
        this.activity = activity
        Log.d("Malar", "Auth Initiated")
    }

    fun createUser(email: String, password: String, onSuccess: () -> Unit, onFailed: () -> Unit) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d("Malar::AuthManager", "createUserWithEmail:success")
                    createUserProfile(email)
                    auth.signOut()
                    onSuccess.invoke()
                } else {
                    onFailed.invoke()
                    Log.w("Malar::AuthManager", "createUserWithEmail:failed", task.exception)
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

    private fun createUserProfile(email: String) {
        val data = hashMapOf(
            "email" to email
        )

        db.collection("users").document(email)
            .set(data)
            .addOnSuccessListener {}
            .addOnFailureListener {
                Log.w("Malar", "createUserProfile:failed:", it)
            }
    }

    fun logout() {
        auth.signOut()
    }
}