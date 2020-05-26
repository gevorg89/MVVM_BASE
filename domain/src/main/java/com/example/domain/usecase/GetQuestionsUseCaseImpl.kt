package com.example.domain.usecase

import androidx.paging.PagedList
import com.example.domain.common.ResultState
import com.example.domain.common.transformer.STransformer
import com.example.domain.entity.Question
import com.example.domain.repository.question.QuestionsRepository
import io.reactivex.Flowable

class GetQuestionsUseCaseImpl(
    private val repository: QuestionsRepository,
    private val transformerSingle: STransformer<ResultState<Int>>
) : GetQuestionsUseCase {
    override fun getQuestions(): Flowable<ResultState<PagedList<Question>>> =
        repository.getQuestions()

    override fun deleteQuestion(question: Question) =
        repository.deleteQuestion(question).compose(transformerSingle)

    override fun trancate(callback: () -> Unit)  = repository.trancate(callback)
}