package com.nabin0.news.presentation.dependencyinjection

import com.nabin0.news.data.database.ArticlesDao
import com.nabin0.news.data.repository.datasource.LocalNewsDataSource
import com.nabin0.news.data.repository.datasourceimpl.LocalNewsDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalDatasourceModule {
    @Singleton
    @Provides
    fun providesLocalDataSource(articlesDao: ArticlesDao): LocalNewsDataSource {
        return LocalNewsDataSourceImpl(articlesDao)
    }
}