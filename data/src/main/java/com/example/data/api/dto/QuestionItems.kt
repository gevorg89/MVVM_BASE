package com.example.data.api.dto

import com.google.gson.annotations.SerializedName

data class QuestionItems(
    @SerializedName("items") val questionItems: List<Question>
)