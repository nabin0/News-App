package com.nabin0.news.data.repository.datasourceimpl

import com.nabin0.news.data.database.ArticlesDao
import com.nabin0.news.data.model.Article
import com.nabin0.news.data.repository.datasource.LocalNewsDataSource
import kotlinx.coroutines.flow.Flow

class LocalNewsDataSourceImpl(
    private val articlesDao: ArticlesDao
) : LocalNewsDataSource {
    override suspend fun saveArticle(article: Article) {
        articlesDao.insert(article)
    }

    override fun getAllSavedArticles(): Flow<List<Article>> {
        return articlesDao.getAllSavedArticles()
    }

    override suspend fun deleteArticle(article: Article) {
        articlesDao.deleteArticle(article)
    }

}