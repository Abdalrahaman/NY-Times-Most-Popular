package com.omranic.nytimesmostpopular.data.model.pojo

data class MostPopularResponse(
    val copyright: String,
    val num_results: Int,
    val results: List<Article>,
    val status: String
)