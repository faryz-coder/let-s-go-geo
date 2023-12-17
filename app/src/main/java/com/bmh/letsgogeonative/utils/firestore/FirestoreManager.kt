package com.bmh.letsgogeonative.utils.firestore

import android.adservices.topics.Topic
import android.util.Log
import com.bmh.letsgogeonative.model.Constant
import com.bmh.letsgogeonative.ui.list_topic.Sections
import com.bmh.letsgogeonative.ui.list_topic.ListTopicViewModel
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
            .addOnCompleteListener { }
            .addOnFailureListener {
                Log.w("Faris", "updateProfile:failed:", it)
            }
    }

    fun getListTopic(selection: String, listTopicViewModel: ListTopicViewModel) {
        val docRef = db.collection(selection)
        docRef.get()
            .addOnSuccessListener { document ->
                val sections = mutableListOf<Sections>()
                document.map {
                    sections.add(
                        Sections(
                            it.id, "", ""
                        )
                    )
                }
                Log.d("Faris", "size :: ${document.size()}")
                listTopicViewModel.setList(sections)
            }
    }

    fun getTopicContent(listTopicViewModel: ListTopicViewModel) {
        val docRef = db.collection(listTopicViewModel.sectionSelected)
            .document(listTopicViewModel.topicSelected)
        val content = emptyList<Constant.TopicContent>().toMutableList()

        docRef.get()
            .addOnSuccessListener { docs ->
                Log.d("Faris", "getTopicContent:success")

                content.add(
                    Constant.TopicContent(
                        docs.getField<String>("title").toString(),
                        docs.getField<String>("notes").toString()
                    )
                )
                listTopicViewModel.setTopicContent(content)
                Log.d("Faris", "getTopicContent: ${content.size}, ${content.size}")
            }
            .addOnFailureListener {
                Log.w("Faris", "getTopicContent:failed:$it")
            }
    }
}