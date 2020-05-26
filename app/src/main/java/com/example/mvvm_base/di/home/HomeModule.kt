package com.example.mvvm_base.di.home

import com.example.data.api.NetworkApi
import com.example.data.datasource.question.QuestionsApiDataSource
import com.example.data.datasource.question.QuestionsApiDataSourceImpl
import com.example.data.datasource.question.QuestionsDatabaseDataSource
import com.example.data.datasource.question.QuestionsDatabaseDataSourceImpl
import com.example.data.db.question.QuestionDao
import com.example.data.repository.question.QuestionsRepositoryImpl
import com.example.domain.repository.question.QuestionsRepository
import com.example.domain.usecase.GetQuestionsUseCase
import com.example.domain.usecase.GetQuestionsUseCaseImpl
import com.example.presentation.common.common.transformer.AsyncSTransformer
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors

@Module
class HomeModule {

    @Provides
    //@PerFragment
    fun provideQuestionDatabaseSource(questionDao: QuestionDao): QuestionsDatabaseDataSource =
        QuestionsDatabaseDataSourceImpl(questionDao, Executors.newSingleThreadExecutor())

    @Provides
    //@PerFragment
    fun provideQuestionApiSource(networkApi: NetworkApi): QuestionsApiDataSource =
        QuestionsApiDataSourceImpl(networkApi)

    @Provides
    //@PerFragment
    fun provideQuestionRepository(
        apiSource: QuestionsApiDataSource,
        databaseSource: QuestionsDatabaseDataSource
    ): QuestionsRepository {
        return QuestionsRepositoryImpl(apiSource, databaseSource)
    }

    @Provides
    //@PerFragment
    fun provideGetQuestionsUseCaseImpl(repository: QuestionsRepository): GetQuestionsUseCase =
        GetQuestionsUseCaseImpl(repository, AsyncSTransformer())
}
