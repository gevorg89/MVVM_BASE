package com.example.domain.entity

data class Question(
    val questionId: Long,
    val owner: Owner,
    val link: String,
    val title: String
) : Entity()