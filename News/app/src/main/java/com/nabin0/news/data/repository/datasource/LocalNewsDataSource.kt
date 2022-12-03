package com.nabin0.news.data.repository.datasource

import com.nabin0.news.data.model.Article
import kotlinx.coroutines.flow.Flow

interface LocalNewsDataSource {
    suspend fun saveArticle(article: Article)
    fun getAllSavedArticles(): Flow<List<Article>>
    suspend fun deleteArticle(article: Article)
}