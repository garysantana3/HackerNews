package com.gosp.apps.hn.data.database.dao

import androidx.room.*
import com.gosp.apps.hn.data.database.entities.NewsEntity

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: NewsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNews(newsList: List<NewsEntity>)

    @Query("SELECT * FROM news_table WHERE hidden = :hidden")
    suspend fun getAllNews(hidden: Boolean = false): List<NewsEntity>?

   @Query("SELECT * FROM news_table WHERE object_id = :id")
    suspend fun getNews(id:String): List<NewsEntity>?

    @Update
    fun updateNews(news: NewsEntity?)

    @Query("DELETE FROM news_table")
    suspend fun deleteAllNews()
}