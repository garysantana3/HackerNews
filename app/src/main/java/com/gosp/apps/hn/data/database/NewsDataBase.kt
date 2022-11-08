package com.gosp.apps.hn.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gosp.apps.hn.data.database.dao.NewsDao
import com.gosp.apps.hn.data.database.entities.NewsEntity

@Database(entities = [NewsEntity::class], version = 1)
abstract class NewsDataBase : RoomDatabase() {

    abstract fun getNewsDao():NewsDao
}