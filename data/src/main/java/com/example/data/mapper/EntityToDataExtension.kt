package com.example.data.mapper

import com.example.data.db.question.QuestionData
import com.example.domain.entity.Owner
import com.example.domain.entity.Question

fun Question.map() = QuestionData.Question(
    question_id = questionId,
    title = title,
    link = link,
    owner = owner.map()
)

fun Owner.map() = QuestionData.Owner(
    user_id = userId,
    displayName = displayName,
    profileImage = profileImage,
    reputation = reputation
)