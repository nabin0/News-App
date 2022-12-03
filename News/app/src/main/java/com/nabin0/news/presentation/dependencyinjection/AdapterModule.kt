package com.nabin0.news.presentation.dependencyinjection

import com.nabin0.news.presentation.adapter.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AdapterModule {

    @Provides
    fun provideNewsAdapter(): NewsAdapter {
        return NewsAdapter()
    }
}