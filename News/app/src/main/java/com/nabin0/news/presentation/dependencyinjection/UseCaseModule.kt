package com.nabin0.news.presentation.dependencyinjection

import com.nabin0.news.domain.repository.NewsRepository
import com.nabin0.news.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun providesGetTopHeadLineUseCase(newsRepository: NewsRepository): GetTopHeadLinesUseCase {
        return GetTopHeadLinesUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun providesGetSearchedNewsUseCase(newsRepository: NewsRepository): GetSearchedNewsUseCase {
        return GetSearchedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun providesSaveNewsUseCase(newsRepository: NewsRepository): SaveNewsUseCase {
        return SaveNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun providesGetSavedNewsUseCase(newsRepository: NewsRepository): GetSavedNewsUseCase {
        return GetSavedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun providesDeleteSavedNewsUseCase(newsRepository: NewsRepository): DeleteSavedNewsUseCase {
        return DeleteSavedNewsUseCase(newsRepository)
    }
}