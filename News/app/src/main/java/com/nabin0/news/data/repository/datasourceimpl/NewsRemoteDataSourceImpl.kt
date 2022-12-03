package com.nabin0.news.data.repository.datasourceimpl

import com.nabin0.news.data.api.NewsAPIService
import com.nabin0.news.data.model.APIResponse
import com.nabin0.news.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService
) : NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse> {
        return newsAPIService.getTopHeadlines(country, page)
    }

    override suspend fun getsearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<APIResponse> {
        return newsAPIService.getSearchedTopHeadlines(country, searchQuery, page)
    }
}