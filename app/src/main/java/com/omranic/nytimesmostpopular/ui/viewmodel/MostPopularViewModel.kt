package com.omranic.nytimesmostpopular.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.omranic.nytimesmostpopular.data.model.Resource
import com.omranic.nytimesmostpopular.data.model.pojo.Article
import com.omranic.nytimesmostpopular.data.model.pojo.response.MostPopularResponse
import com.omranic.nytimesmostpopular.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MostPopularViewModel @Inject constructor(private val mainRepository: MainRepository) :
    BaseViewModel() {

    val mostPopularResult: MutableLiveData<Resource<List<Article>>> = MutableLiveData()

    private val _articleItemSelected = MutableLiveData<Article>()
    val articleItemSelected: LiveData<Article>
        get() = _articleItemSelected

    fun getMostPopularArticlesView(period: Int) =
        viewModelScope.launch {
            if (setOf(1, 7, 30).contains(period)) {
                getMostPopularArticlesFromNetwork(period)
            }
        }

    private suspend fun getMostPopularArticlesFromNetwork(
        period: Int
    ) {
        mostPopularResult.postValue(Resource.Loading())
        try {
            val response = mainRepository.getMostViewedArticles(period)
            mostPopularResult.postValue(handleMostViewedArticlesResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> mostPopularResult.postValue(Resource.Error("Network Failure"))
                else -> mostPopularResult.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleMostViewedArticlesResponse(response: Response<MostPopularResponse>): Resource<List<Article>> {
        if (response.isSuccessful) {
            response.body()?.let {
                if (it.status == "OK") {
                    return Resource.Success(it.results)
                }
            }
        } else if (response.code() == 400) {
            val errorResponse =
                Gson().fromJson(response.errorBody()!!.string(), MostPopularResponse::class.java)
            return Resource.Error(errorResponse.status)
        }
        return Resource.Error("error" + response.message())
    }


    fun onArticleItemSelected(article: Article) {
        _articleItemSelected.value = article
    }
}