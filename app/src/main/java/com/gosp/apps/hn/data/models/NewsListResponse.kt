package com.gosp.apps.hn.data.models

import com.gosp.apps.hn.data.database.entities.NewsEntity

data class NewsListResponse (val hits: ArrayList<NewsEntity>)