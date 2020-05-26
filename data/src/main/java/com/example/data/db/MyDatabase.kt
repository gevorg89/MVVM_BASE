package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.question.QuestionDao
import com.example.data.db.question.QuestionData

@Database(entities = [QuestionData.Question::class], version = 1, exportSchema = false)
abstract class MyDatabase() : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}