package com.gosp.apps.hn.di

import android.content.Context
import androidx.room.Room
import com.gosp.apps.hn.data.database.NewsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val NEWS_DATABASE_NAME = "news_database"

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context,NewsDataBase::class.java,NEWS_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideNewsDao(db: NewsDataBase) = db.getNewsDao()

}