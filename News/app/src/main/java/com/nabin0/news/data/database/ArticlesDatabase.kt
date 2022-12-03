package com.nabin0.news.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nabin0.news.data.model.Article

@Database(entities = [Article::class], exportSchema = false, version = 1)
@TypeConverters(Converters::class)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticlesDao
}