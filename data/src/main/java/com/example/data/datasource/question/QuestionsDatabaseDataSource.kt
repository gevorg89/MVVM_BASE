package com.example.data.datasource.question

import androidx.paging.DataSource
import com.example.data.datasource.BaseDataSource
import com.example.domain.entity.Question
import io.reactivex.Single

interface QuestionsDatabaseDataSource : BaseDataSource {
    fun getQuestions(): DataSource.Factory<Int, Question>
    fun persist(questions: List<Question>, insertFinished: () -> Unit)
    fun deleteQuestion(question: Question) : Single<Int>
    fun trancate(callback: () -> Unit)
}