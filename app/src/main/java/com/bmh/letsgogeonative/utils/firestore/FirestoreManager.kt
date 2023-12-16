package com.bmh.letsgogeonative.utils.firestore

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.getField

class FirestoreManager {
    private val db: FirebaseFirestore = Firebase.firestore
    private val auth: FirebaseAuth = Firebase.auth

    fun userProfile(setProfile: (String, String) -> Unit) {
        val docRef = db.collection("users").document(auth.currentUser?.email.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                setProfile(
                    document.getField<String>("email").toString(),
                    document.getField<String>("name") ?: ""
                )
            }
    }

    fun updateProfile(name: String) {
        val data = hashMapOf(
            "name" to name
        )

        db.collection("users").document(auth.currentUser?.email.toString())
            .set(data, SetOptions.merge())
            .addOnCompleteListener {  }
            .addOnFailureListener {
                Log.w("Faris", "updateProfile:failed:", it)
            }
    }
}