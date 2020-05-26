package com.example.data.repository.question

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.example.data.datasource.question.QuestionsApiDataSource
import com.example.data.datasource.question.QuestionsDatabaseDataSource
import com.example.data.extension.applyIoScheduler
import com.example.data.repository.BaseRepositoryImpl
import com.example.domain.common.ResultState
import com.example.domain.entity.Question
import com.example.domain.repository.question.QuestionsRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single

class QuestionsRepositoryImpl(
    private val apiSource: QuestionsApiDataSource,
    private val databaseSource: QuestionsDatabaseDataSource
) : BaseRepositoryImpl<Question>(), QuestionsRepository {
    override fun getQuestions(): Flowable<ResultState<PagedList<Question>>> {
        val dataSourceFactory = databaseSource.getQuestions()
        val boundaryCallback = RepoBoundaryCallback(apiSource, databaseSource)
        val config = PagedList.Config.Builder()
            .setPrefetchDistance(DATABASE_PAGE_SIZE)
            .setInitialLoadSizeHint(DATABASE_PAGE_SIZE)
            .setPageSize(DATABASE_PAGE_SIZE).build()
        val data = RxPagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .buildFlowable(BackpressureStrategy.BUFFER)
        return data
            .applyIoScheduler()
            .map { d ->
                when (d.isNotEmpty()) {
                    true -> ResultState.Success(d)
                    false -> ResultState.Loading(d)
                }
            }
            .onErrorReturn { e -> ResultState.Error(e, null) }
    }

    override fun deleteQuestion(question: Question): Single<ResultState<Int>> =
        databaseSource.deleteQuestion(question).map {
            ResultState.Success(it) as ResultState<Int>
        }.onErrorReturn {
            ResultState.Error(it, null)
        }

    override fun trancate(callback: () -> Unit) {
        databaseSource.trancate { callback() }
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 30
    }

}