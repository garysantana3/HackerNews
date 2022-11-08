package com.gosp.apps.hn.data.models

data class NewsModel (
    val story_id: String,
    val story_title:String,
    val story_url:String,
    val author:String,
    val created_at:String,
    val visible:Boolean?= true)