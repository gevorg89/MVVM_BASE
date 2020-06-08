package com.example.domain.common

import androidx.lifecycle.LiveData

/**
 * Created by Dr.jacky on 10/9/2018.
 */
/**
 * A wrapper for database and network states.
 */
sealed class ResultState<T>(open val networkError: LiveData<Throwable>? = null) {

    /**
     * A state of [data] which shows that we know there is still an update to come.
     */
    data class Loading<T>(val data: T, override val networkError: LiveData<Throwable>? = null) : ResultState<T>(networkError)

    /**
     * A state that shows the [data] is the last known update.
     */
    data class Success<T>(val data: T) : ResultState<T>(null)

    /**
     * A state to show a [throwable] is thrown.
     */
    data class Error<T>(val throwable: Throwable, val data: T?) : ResultState<T>()
}