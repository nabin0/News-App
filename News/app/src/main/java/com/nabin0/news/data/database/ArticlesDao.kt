package com.nabin0.news.data.database

import androidx.room.*
import com.nabin0.news.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllSavedArticles(): Flow<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}