package com.omranic.nytimesmostpopular.repositories

import com.omranic.nytimesmostpopular.data.model.pojo.response.MostPopularResponse
import com.omranic.nytimesmostpopular.data.repository.NyTimesRepository
import retrofit2.Response

class MainRepositoryTest : NyTimesRepository {
    override suspend fun getMostViewedArticles(period: Int): Response<MostPopularResponse> {
        TODO("Not yet implemented")
    }
}