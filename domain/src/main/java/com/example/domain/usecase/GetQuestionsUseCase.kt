package com.example.domain.usecase

import androidx.paging.PagedList
import com.example.domain.common.ResultState
import com.example.domain.entity.Question
import io.reactivex.Flowable
import io.reactivex.Single

interface GetQuestionsUseCase : BaseUseCase {
    fun getQuestions(): Flowable<ResultState<PagedList<Question>>>
    fun deleteQuestion(question: Question): Single<ResultState<Int>>
    fun trancate(callback: () -> Unit)
}