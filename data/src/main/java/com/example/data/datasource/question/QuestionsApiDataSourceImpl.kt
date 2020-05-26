package com.example.data.datasource.question

import com.example.data.api.NetworkApi
import com.example.data.extension.applyIoScheduler
import com.example.data.mapper.map
import com.example.domain.entity.Question
import io.reactivex.Single

class QuestionsApiDataSourceImpl(private val api: NetworkApi) : QuestionsApiDataSource {
    override fun getQuestions(page: Int, pageSize: Int): Single<List<Question>> =
        api.getQuestions(page, pageSize).applyIoScheduler()
            .map { item -> item.questionItems.map { it.map() } }
}