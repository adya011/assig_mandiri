package com.nanda.remote.api

import com.nanda.remote.model.EverythingDto
import com.nanda.remote.model.TopHeadlineDto
import com.nanda.remote.util.API_KEY
import com.nanda.remote.util.PUBLISHED_AT
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    fun everything(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("sortBy") sortBy: String = PUBLISHED_AT,
        @Query("apiKey") apiKey: String = API_KEY
    ): Deferred<Response<EverythingDto>>

    @GET("top-headlines/sources")
    fun topHeadlines(
        @Query("category") category: String
    ): Deferred<Response<TopHeadlineDto>>
}