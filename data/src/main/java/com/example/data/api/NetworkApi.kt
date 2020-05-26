package com.example.data.api

import com.example.data.api.dto.QuestionItems
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    @GET("/2.2/questions?order=desc&sort=activity&tagged=android&site=stackoverflow")
    fun getQuestions(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): Single<QuestionItems>
}