package com.example.data.datasource.question

import com.example.data.db.question.QuestionDao
import com.example.data.mapper.map
import com.example.domain.entity.Question
import java.lang.Exception
import java.util.concurrent.Executor

class QuestionsDatabaseDataSourceImpl(
    private val questionDao: QuestionDao,
    private val ioExecutor: Executor
) : QuestionsDatabaseDataSource {
    override fun getQuestions() = questionDao.selectAllPaged().map { it.map() }

    override fun persist(questions: List<Question>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            questionDao.insert(questions.map { it.map() })
            insertFinished()
        }
    }

    override fun deleteQuestion(question: Question) = questionDao.delete(question.map())

    override fun trancate(callback: () -> Unit) {
        ioExecutor.execute {
            questionDao.truncate()
            callback()
        }
    }

}