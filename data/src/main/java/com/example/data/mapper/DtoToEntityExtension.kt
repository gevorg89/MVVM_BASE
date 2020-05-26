package com.example.data.mapper

import com.example.domain.entity.Owner
import com.example.domain.entity.Question
import com.example.data.api.dto.Owner as OwnerDto
import com.example.data.api.dto.Question as QuestionDto

fun QuestionDto.map() = Question(
    questionId = questionId,
    owner = owner.map(),
    link = link,
    title = title
)

fun OwnerDto.map() = Owner(
    reputation = reputation,
    profileImage = profileImage?:"",
    displayName = displayName,
    userId = userId
)
