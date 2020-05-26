package com.example.data.datasource.question

import android.annotation.SuppressLint
import com.example.data.datasource.BaseDataSource
import com.example.domain.common.ResultState
import com.example.domain.entity.Question
import com.example.domain.entity.QuestionItems
import io.reactivex.Single

@SuppressLint("CheckResult")
fun getQuestions(
    apiSource: QuestionsApiDataSource, page: Int,
    itemsPerPage: Int,
    onResult: (result: ResultState<List<Question>>) -> Unit
) {
    apiSource.getQuestions(page, itemsPerPage)
        .subscribe({ data ->
            onResult(ResultState.Success(data))
        }, { throwable ->
            onResult(ResultState.Error(throwable, null))
        })
}

interface QuestionsApiDataSource : BaseDataSource {
    fun getQuestions(page: Int, pageSize: Int): Single<List<Question>>
}