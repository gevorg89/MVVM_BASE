package com.example.domain.entity

data class Owner(
    val reputation: Int,
    val userId: Long,
    val profileImage: String,
    val displayName: String
)