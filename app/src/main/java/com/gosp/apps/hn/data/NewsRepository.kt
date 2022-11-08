package com.gosp.apps.hn.data

import com.gosp.apps.hn.api.NewsServices
import com.gosp.apps.hn.data.database.dao.NewsDao
import com.gosp.apps.hn.data.database.entities.NewsEntity
import com.gosp.apps.hn.data.models.NewsListResponse
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsDao: NewsDao,private val newsServices: NewsServices) {

    suspend fun getAllNewsFromDataBase(): List<NewsEntity>? {
        return  newsDao.getAllNews()
    }

    suspend fun getAllNewsFromApi(): NewsListResponse? {
        return newsServices.getNewsItems()
    }

    suspend fun getNewsFromDataBase(id: String ): List<NewsEntity>? {
        return  newsDao.getNews(id)
    }

    suspend fun insertNews(news: NewsEntity){
        newsDao.insertNews(news)
    }

    suspend fun insertAllNews(news: List<NewsEntity>){
        newsDao.insertAllNews(news)
    }

    suspend fun hideNews(hide: NewsEntity){
        newsDao.updateNews(hide)
    }
}