package com.example.data.repository.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.data.datasource.question.QuestionsApiDataSource
import com.example.data.datasource.question.QuestionsDatabaseDataSource
import com.example.data.datasource.question.getQuestions
import com.example.domain.common.ResultState
import com.example.domain.entity.Question

class RepoBoundaryCallback(
    private val apiSource: QuestionsApiDataSource,
    private val databaseSource: QuestionsDatabaseDataSource
) : PagedList.BoundaryCallback<Question>() {

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 0
    private val _networkErrors = MutableLiveData<String>()

    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * Database returned 0 items. We should send request to the backend for more items.
     */
    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }

    /**
     * When all items in the database were loaded, we need to send a request to the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: Question) {
        requestAndSaveData()
    }

    override fun onItemAtFrontLoaded(itemAtFront: Question) {
        super.onItemAtFrontLoaded(itemAtFront)
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true
        getQuestions(apiSource, lastRequestedPage, NETWORK_PAGE_SIZE) { questions ->
            when (questions) {
                is ResultState.Success -> {
                    databaseSource.persist(questions.data) {
                        lastRequestedPage++
                        isRequestInProgress = false
                    }
                }
                is ResultState.Error -> {
                    _networkErrors.postValue(questions.throwable.message)
                    isRequestInProgress = false
                }
            }
        }
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 30
    }
}