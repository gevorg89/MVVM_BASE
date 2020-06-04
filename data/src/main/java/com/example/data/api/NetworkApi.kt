package com.example.data.api

import com.example.data.api.dto.Question
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    //?page=0&size=10&max=1000
    @GET("/mvvm_android/")
    fun getQuestions(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("max") max: Int = 1000
    ): Single<List<Question>>
}