package com.bmh.letsgogeonative.model

class Constant {
    data class TopicContent (
        val title : String,
        val noteUrl: String
    )

    data class Question (
        var question: List<String>,
        val option: List<List<String>>,
        val answer: List<Long>
    )

    data class Event (
        val imgUrl: String,
        val dateTime: String
    )

    data class User (
        var email: String,
        var image: String
    )

    data class Score (
        val result: Long,
        val section: String,
        val topic: String,
        val totalQuestion: Long
    )
}