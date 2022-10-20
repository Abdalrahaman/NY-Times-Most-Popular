package com.omranic.nytimesmostpopular.data.remote

import com.omranic.nytimesmostpopular.BuildConfig
import com.omranic.nytimesmostpopular.data.model.pojo.response.MostPopularResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("svc/mostpopular/v2/viewed/{period}.json")
    suspend fun getMostViewedArticles(
        @Path("period") period: Int,
        @Query("api-key") apiKey: String = BuildConfig.API_KEY
    ): Response<MostPopularResponse>
}