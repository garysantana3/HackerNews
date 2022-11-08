package com.gosp.apps.hn.data.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class NewsEntity (
                  @PrimaryKey
                  @ColumnInfo(name = "object_id")
                  val objectID: String,
                  @ColumnInfo(name = "story_id")
                  val story_id: String?,
                  @ColumnInfo(name = "title")
                  val story_title:String?,
                  @ColumnInfo(name = "url")
                  val story_url:String?,
                  @ColumnInfo(name = "author")
                  val author:String?,
                  @ColumnInfo(name = "date")
                  val created_at:String?,
                  @ColumnInfo(name = "hidden")
                  var hidden:Boolean)
