package com.nabin0.news.domain.repository

import com.nabin0.news.data.model.APIResponse
import com.nabin0.news.data.model.Article
import com.nabin0.news.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopHeadlines(country: String, page: Int): Resource<APIResponse>
    suspend fun getSearchedNews(country: String, searchQuery: String, page: Int): Resource<APIResponse>
    suspend fun saveNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>
    suspend fun deleteNews(article: Article)
}