package com.bmh.letsgogeonative.model

import com.google.gson.annotations.SerializedName

class Question {
    data class SetQuestion(
        @SerializedName("questions")
        val questions: List<Questions>
    )

    data class Questions(
        @SerializedName("question")
        val question: String,
        @SerializedName("options")
        val option: Options,
        @SerializedName("correct_answer")
        val answer: String
    )

    data class Options(
        @SerializedName("A")
        val A: String,
        @SerializedName("B")
        val B: String,
        @SerializedName("C")
        val C: String,
        @SerializedName("D")
        val D: String,
    )
}