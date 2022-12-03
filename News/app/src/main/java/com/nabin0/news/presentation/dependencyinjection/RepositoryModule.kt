package com.nabin0.news.presentation.dependencyinjection

import com.nabin0.news.data.repository.NewsRepositoryImpl
import com.nabin0.news.data.repository.datasource.LocalNewsDataSource
import com.nabin0.news.data.repository.datasource.NewsRemoteDataSource
import com.nabin0.news.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: LocalNewsDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }

}