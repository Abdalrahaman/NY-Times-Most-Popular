package com.omranic.nytimesmostpopular.data.repository

import com.omranic.nytimesmostpopular.data.model.pojo.MostPopularResponse
import com.omranic.nytimesmostpopular.data.remote.ApiService
import com.omranic.nytimesmostpopular.utils.Constants.Companion.API_KEY
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getMostViewedArticles(
        period: Int
    ): Response<MostPopularResponse> = apiService.getMostViewedArticles(period, API_KEY)
}