package com.bmh.letsgogeonative.utils.storage

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class StorageManager {
    private val storage = Firebase.storage
    private val auth = Firebase.auth

    fun uploadImg(uri: Uri, onSuccess: (String) -> Unit, onFailed: () -> Unit) {
        // Create a storage reference from our app
        val storageRef = storage.reference
        val profileRef = storageRef.child("profle/${uri.lastPathSegment}")

        val uploadTask = profileRef.putFile(uri)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            profileRef.downloadUrl
        }
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("StorageManager", "successs")
                val downloadUri = task.result
                onSuccess.invoke(downloadUri.toString())
            }
        }
        .addOnFailureListener {
            onFailed.invoke()
        }
    }
}