package com.omranic.nytimesmostpopular.data.repository

import com.omranic.nytimesmostpopular.data.model.pojo.response.MostPopularResponse
import retrofit2.Response

interface NyTimesRepository {

    suspend fun getMostViewedArticles(period: Int) : Response<MostPopularResponse>
}