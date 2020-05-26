package com.example.data.api.dto

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("question_id") val questionId: Long,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("link") val link: String,
    @SerializedName("title") val title: String
)