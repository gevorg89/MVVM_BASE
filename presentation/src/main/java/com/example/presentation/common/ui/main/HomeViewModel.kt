package com.example.presentation.common.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.example.domain.common.ResultState
import com.example.domain.entity.Question
import com.example.domain.usecase.GetQuestionsUseCase
import com.example.presentation.common.common.OperationLiveData
import com.example.presentation.common.common.extension.applyComputationScheduler
import com.example.presentation.common.ui.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val getQuestionsUseCase: GetQuestionsUseCase) :
    BaseViewModel() {
    private var tempDispossable: Disposable? = null
    private val questionToBeDeleted = MutableLiveData<Question>()
    private val fetch = MutableLiveData<String>()
    private var processLoadingTimer: Disposable? = null

    val deletedQuestionLiveData: LiveData<ResultState<Int>> =
        Transformations.switchMap(questionToBeDeleted) {
            OperationLiveData<ResultState<Int>> {
                getQuestionsUseCase.deleteQuestion(it).toFlowable().subscribe { resultState ->
                    postValue(resultState)
                }
            }
        }

    fun deleteQuestion(question: Question) {
        questionToBeDeleted.postValue(question)
    }

    val questionsLiveData: LiveData<ResultState<PagedList<Question>>> =
        Transformations.switchMap(fetch) {
            OperationLiveData<ResultState<PagedList<Question>>> {

                if (tempDispossable?.isDisposed != true) {
                    tempDispossable?.dispose()
                }
                tempDispossable = getQuestionsUseCase.getQuestions().subscribe { resultState ->
                    postValue((resultState))
                }
                tempDispossable?.track()
            }
        }

    val networkErrors: LiveData<Throwable> =
        Transformations.switchMap(questionsLiveData) { resultState ->
            resultState.networkError
        }

    val processLoading = MutableLiveData<Boolean>()

    fun getQuestions() {
        processLoadingTimer?.dispose()
        processLoadingTimer?.delete()
        processLoadingTimer = Observable.timer(200, TimeUnit.MILLISECONDS)
            .applyComputationScheduler()
            .subscribe {
                if (questionsLiveData.value == null) {
                    processLoading.postValue(true)
                }
            }
        processLoadingTimer?.track()
        fetch.postValue("")
    }

    fun invalidate() {
        getQuestionsUseCase.trancate {
            getQuestions()
        }
    }
}