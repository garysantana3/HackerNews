package com.gosp.apps.hn.di

import com.gosp.apps.hn.api.Endpoints
import com.gosp.apps.hn.api.NewsApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private fun initInterceptor(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        val httpClient = OkHttpClient.Builder()

        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(30, TimeUnit.SECONDS)

        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpClient.addInterceptor(logging)
    }

    @Singleton
    @Provides
    fun providerRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Endpoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(initInterceptor().build())
            .build()
    }

    @Singleton
    @Provides
    fun providerApiClient(retrofit: Retrofit) : NewsApiClient {
        return retrofit.create(NewsApiClient::class.java)
    }
}