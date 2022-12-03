package com.nabin0.news.data.repository

import com.nabin0.news.data.model.APIResponse
import com.nabin0.news.data.model.Article
import com.nabin0.news.data.repository.datasource.LocalNewsDataSource
import com.nabin0.news.data.repository.datasource.NewsRemoteDataSource
import com.nabin0.news.data.util.Resource
import com.nabin0.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: LocalNewsDataSource
) : NewsRepository {
    override suspend fun getTopHeadlines(country: String, page: Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country, page))
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getsearchedNews(country, searchQuery, page))
    }

    private fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticle(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getAllSavedArticles()
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDataSource.deleteArticle(article)
    }

}