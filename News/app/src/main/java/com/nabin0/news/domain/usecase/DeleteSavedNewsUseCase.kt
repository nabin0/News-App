package com.nabin0.news.domain.usecase

import com.nabin0.news.data.model.Article
import com.nabin0.news.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) = newsRepository.deleteNews(article)
}