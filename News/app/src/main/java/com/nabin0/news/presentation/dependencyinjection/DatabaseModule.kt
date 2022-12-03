package com.nabin0.news.presentation.dependencyinjection

import android.app.Application
import androidx.room.Room
import com.nabin0.news.data.database.ArticlesDao
import com.nabin0.news.data.database.ArticlesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesNewsDatabase(application: Application): ArticlesDatabase {
        return Room.databaseBuilder(application, ArticlesDatabase::class.java, "article_db")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesDao(articlesDatabase: ArticlesDatabase): ArticlesDao {
        return articlesDatabase.getArticleDao()
    }
}