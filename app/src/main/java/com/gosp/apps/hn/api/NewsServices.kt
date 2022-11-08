package com.gosp.apps.hn.api
import com.gosp.apps.hn.data.models.NewsListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class NewsServices @Inject constructor(private val api: NewsApiClient) {

    suspend fun getNewsItems(): NewsListResponse? {
        return withContext(Dispatchers.IO) {
            val response = api.searchNews()
            response.body()
        }
    }
}