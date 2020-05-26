package com.example.data.mapper

import com.example.data.db.question.QuestionData
import com.example.domain.entity.Owner
import com.example.domain.entity.Question

fun QuestionData.Question.map() = Question(
    questionId = question_id,
    title = title,
    link = link,
    owner = owner.map()
)

fun QuestionData.Owner.map() = Owner(
    reputation = reputation,
    userId = user_id,
    displayName = displayName,
    profileImage = profileImage
)