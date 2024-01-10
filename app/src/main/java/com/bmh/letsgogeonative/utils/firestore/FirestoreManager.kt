package com.bmh.letsgogeonative.utils.firestore

import android.util.Log
import com.bmh.letsgogeonative.model.Constant
import com.bmh.letsgogeonative.model.Question
import com.bmh.letsgogeonative.ui.home.HomeViewModel
import com.bmh.letsgogeonative.ui.list_topic.Sections
import com.bmh.letsgogeonative.ui.list_topic.ListTopicViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.getField

class FirestoreManager {
    private val db: FirebaseFirestore = Firebase.firestore
    private val auth: FirebaseAuth = Firebase.auth

    fun userProfile(setProfile: (String, String, String) -> Unit) {
        val docRef = db.collection("users").document(auth.currentUser?.email.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                setProfile(
                    document.getField<String>("email").toString(),
                    document.getField<String>("name") ?: "",
                    document.getField<String>("image") ?: ""
                )
            }
    }

    fun checkUserIsAdmin(email: String, onSuccess: () -> Unit, onFailed: () -> Unit) {
        db.collection("admin")
            .get()
            .addOnSuccessListener { snapshot ->
                snapshot.map {
                    if (it.id == email) {
                        onSuccess.invoke()
                        return@addOnSuccessListener
                    }
                }
                onFailed.invoke()
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
                Log.w("FirestoreManager", "updateProfile:failed:", it)
            }
    }

    fun updateProfileImage(downloadUrl: String) {
        val data = hashMapOf(
            "image" to downloadUrl
        )
        db.collection("users").document(auth.currentUser?.email.toString())
            .set(data, SetOptions.merge())
            .addOnCompleteListener { }
            .addOnFailureListener {
                Log.w("FirestoreManager", "updateProfile:failed:", it)
            }

    }

    fun getListTopic(selection: String, listTopicViewModel: ListTopicViewModel) {
        val docRef = db.collection(selection).orderBy("title", Query.Direction.ASCENDING)
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
                Log.d("FirestoreManager", "size :: ${document.size()}")
                listTopicViewModel.setList(sections)
            }
    }

    fun removeTopic(section: String, topic: String, onSuccess: () -> Unit) {
        db.collection(section).document(topic)
            .delete()
            .addOnSuccessListener {
                onSuccess.invoke()
            }
    }

    fun getTopicContent(listTopicViewModel: ListTopicViewModel) {
        val docRef = db.collection(listTopicViewModel.sectionSelected)
            .document(listTopicViewModel.topicSelected)
        val content = emptyList<Constant.TopicContent>().toMutableList()

        docRef.get()
            .addOnSuccessListener { docs ->
                Log.d("FirestoreManager", "getTopicContent:success")

                content.add(
                    Constant.TopicContent(
                        docs.getField<String>("title").toString(),
                        docs.getField<String>("notes").toString()
                    )
                )
                listTopicViewModel.setTopicContent(content)
                Log.d("FirestoreManager", "getTopicContent: ${content.size}, ${content.size}")
            }
            .addOnFailureListener {
                Log.w("FirestoreManager", "getTopicContent:failed:$it")
            }
    }

    fun getQuestion(listTopicViewModel: ListTopicViewModel) {
        val docRef = db.collection(listTopicViewModel.sectionSelected)
            .document(listTopicViewModel.topicSelected)
            .collection("question")


        val listQuestion = emptyList<String>().toMutableList()
        val listOption = emptyList<List<String>>().toMutableList()
        val listAnswer = emptyList<Long>().toMutableList()

        docRef.get()
            .addOnSuccessListener { documents ->

                for (doc in documents) {
                    doc.get("option")
                    Log.d("getQuestion", "${(doc.get("option") as List<*>).get(0)}")
                }

                documents.map {
                    val question = it.getField<String>("question").toString()
                    val option = it.get("option") as List<String>
                    val answer = it.get("answer") as Long

                    listQuestion.add(question)
                    listOption.add(option)
                    listAnswer.add(answer)
                }.let {
                    listTopicViewModel.setQuestion(
                        Constant.Question(
                            listQuestion,
                            listOption,
                            listAnswer
                        )
                    )
                }

            }
    }

    fun submitResult(listTopicViewModel: ListTopicViewModel) {
        val data = hashMapOf(
            "section" to listTopicViewModel.sectionSelected,
            "result" to listTopicViewModel.marks,
            "topic" to listTopicViewModel.topicSelected,
            "totalQuestion" to listTopicViewModel.setsQuestion.value!!.question.size
        )

        db.collection("users")
            .document(auth.currentUser?.email.toString())
            .collection("result")
            .document(listTopicViewModel.sectionSelected)
            .collection("list")
            .document(listTopicViewModel.topicSelected)
            .set(data)
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }

    fun getListEvent(homeViewModel: HomeViewModel) {
        val docRef = db.collection("event")

        docRef.get()
            .addOnSuccessListener { document ->
                val event = mutableListOf<Constant.Event>()
                document.map {
                    event.add(
                        Constant.Event(
                            it.getField<String>("imgUrl").toString(),
                            it.getField<String>("dateTime").toString()
                        )
                    )
                }
                Log.d("FirestoreManager", "size :: ${document.size()}")
                homeViewModel.setEvent(event)
            }
    }

    fun getListAnnouncement(homeViewModel: HomeViewModel) {
        val docRef = db.collection("annoucement")

        docRef.get()
            .addOnSuccessListener { document ->
                val event = mutableListOf<Constant.Event>()
                document.map {
                    event.add(
                        Constant.Event(
                            it.getField<String>("imgUrl").toString(),
                            it.getField<String>("dateTime").toString()
                        )
                    )
                }
                Log.d("FirestoreManager", "size :: ${document.size()}")
                homeViewModel.setAnnouncement(event)
            }
    }

    fun getResult(section: String, setScore: (MutableList<Constant.Score>) -> Unit) {
        val docRef = db.collection("users")
            .document(auth.currentUser?.email.toString())
            .collection("result")
            .document(section)
            .collection("list")

        docRef.get()
            .addOnSuccessListener { document ->
                val score = mutableListOf<Constant.Score>()

                document.map {
                    score.add(
                        Constant.Score(
                            it.getField<Long>("result")!!,
                            it.getField<String>("section")!!,
                            it.getField<String>("topic")!!,
                            it.getField<Long>("totalQuestion")!!

                        )
                    )
                }
                setScore.invoke(score)
            }
    }

    fun uploadQuestion(
        section: String,
        title: String,
        notes: String,
        questions: Question.SetQuestion,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
    ) {
        var i = 0
        questions.questions.map { item ->
            val question = item.question
            val option = item.option
            val answer = item.answer

            val field = hashMapOf(
                "notes" to notes,
                "title" to title
            )
            db.collection(section).document(title)
                .set(field)
                .addOnSuccessListener {
                    val setOption = arrayListOf(
                        option.A, option.B, option.C, option.D
                    )
                    var ans = 0

                    when (answer) {
                        "A" -> {
                            ans = 0
                        }

                        "B" -> {
                            ans = 1
                        }

                        "C" -> {
                            ans = 2
                        }

                        "D" -> {
                            ans = 3
                        }
                    }

                    val setQuestion = hashMapOf(
                        "answer" to ans,
                        "option" to setOption,
                        "question" to question
                    )
                    db.collection(section).document(title).collection("question")
                        .document((i + 1).toString())
                        .set(setQuestion)
                        .addOnSuccessListener {
                            onSuccess.invoke()
                        }
                        .addOnFailureListener {
                            onFailed.invoke()
                        }
                    i += 1
                }
                .addOnFailureListener {
                    onFailed.invoke()
                }
        }
    }
}