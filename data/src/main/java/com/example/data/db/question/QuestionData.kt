package com.example.data.db.question

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class QuestionData {
    @Entity(tableName = "question")
    data class Question(
        @ColumnInfo(name = "question_id") @PrimaryKey(autoGenerate = false) val question_id: Long,
        @Embedded val owner: Owner,
        @ColumnInfo(name = "link") val link: String,
        @ColumnInfo(name = "title") val title: String
    ) : QuestionData()

    @Entity(tableName = "owner")
    data class Owner(
        @ColumnInfo(name = "user_id") @PrimaryKey(autoGenerate = false) val user_id: Long,
        @ColumnInfo(name = "reputation") val reputation: Int,
        @ColumnInfo(name = "profile_image") val profileImage: String,
        @ColumnInfo(name = "display_name") val displayName: String
    ) : QuestionData()
}