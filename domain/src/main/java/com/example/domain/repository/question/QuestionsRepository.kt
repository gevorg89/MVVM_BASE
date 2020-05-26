package com.example.domain.repository.question

import androidx.paging.PagedList
import com.example.domain.common.ResultState
import com.example.domain.entity.Question
import com.example.domain.repository.BaseRepository
import io.reactivex.Flowable
import io.reactivex.Single

interface QuestionsRepository : BaseRepository {
    fun getQuestions(): Flowable<ResultState<PagedList<Question>>>
    fun deleteQuestion(question: Question): Single<ResultState<Int>>
    fun trancate(callback: () -> Unit)
}