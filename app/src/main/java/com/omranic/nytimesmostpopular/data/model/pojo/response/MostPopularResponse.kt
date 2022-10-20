package com.omranic.nytimesmostpopular.data.model.pojo.response

import com.omranic.nytimesmostpopular.data.model.pojo.Article

data class MostPopularResponse(
    val copyright: String,
    val num_results: Int,
    val results: List<Article>,
    val status: String
)