package com.nabin0.news.domain.usecase

import com.nabin0.news.data.model.APIResponse
import com.nabin0.news.data.model.Article
import com.nabin0.news.data.util.Resource
import com.nabin0.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country: String, searchQuery: String, page: Int): Resource<APIResponse> {
        return newsRepository.getSearchedNews(country, searchQuery, page)
    }
}