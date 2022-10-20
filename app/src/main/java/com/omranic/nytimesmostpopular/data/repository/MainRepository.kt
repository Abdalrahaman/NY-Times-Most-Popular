package com.omranic.nytimesmostpopular.data.repository

import com.omranic.nytimesmostpopular.data.model.pojo.response.MostPopularResponse
import com.omranic.nytimesmostpopular.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) : NyTimesRepository{

    //    suspend fun getMostViewedArticles(
//        period: Int
//    ): Response<MostPopularResponse> = apiService.getMostViewedArticles(period)
    override suspend fun getMostViewedArticles(period: Int): Response<MostPopularResponse> {
        return apiService.getMostViewedArticles(period)
    }
}