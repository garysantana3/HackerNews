package com.gosp.apps.hn.api

import com.gosp.apps.hn.data.models.NewsListResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsApiClient {

    @GET(Endpoints.NEWS_LIST)
    suspend fun searchNews(): Response<NewsListResponse>
}