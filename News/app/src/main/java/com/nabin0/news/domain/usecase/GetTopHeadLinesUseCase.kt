package com.nabin0.news.domain.usecase

import com.nabin0.news.data.model.APIResponse
import com.nabin0.news.data.util.Resource
import com.nabin0.news.domain.repository.NewsRepository

class GetTopHeadLinesUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country: String, page: Int): Resource<APIResponse> {
        return newsRepository.getTopHeadlines(country, page)
    }
}