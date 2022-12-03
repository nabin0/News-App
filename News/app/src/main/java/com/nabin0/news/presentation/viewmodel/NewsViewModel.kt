package com.nabin0.news.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.nabin0.news.data.model.APIResponse
import com.nabin0.news.data.model.Article
import com.nabin0.news.data.util.Resource
import com.nabin0.news.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app: Application, private val getTopHeadLinesUseCase: GetTopHeadLinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : AndroidViewModel(app) {
    val newsHeadlines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadLines(country: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            newsHeadlines.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    val apiResult = getTopHeadLinesUseCase.execute(country, page)
                    newsHeadlines.postValue(apiResult)
                } else {
                    newsHeadlines.postValue(Resource.Error("Internet Not Available"))
                }
            } catch (e: Exception) {
                newsHeadlines.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        if (context != null) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
                    }
                }
            }
        }
        return false
    }

    // Search News

    val searchedNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getSearchedNewsHeadLines(country: String, searchQuery: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            searchedNews.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    val apiResult = getSearchedNewsUseCase.execute(country, searchQuery, page)
                    searchedNews.postValue(apiResult)
                } else {
                    searchedNews.postValue(Resource.Error("Internet Not Available"))
                }
            } catch (e: Exception) {
                searchedNews.postValue(Resource.Error("An Error Occurred: ${e.message.toString()}"))
            }
        }
    }

    //Local database operations
    // Save article
    fun saveArticle(article: Article) {
        viewModelScope.launch {
            saveNewsUseCase.execute(article)
        }
    }

    fun getSavedNews() = liveData {
        getSavedNewsUseCase.execute().collect {
            Log.d("MyTag", it.toString())
            emit(it)
        }
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            deleteSavedNewsUseCase.execute(article)
        }
    }

}