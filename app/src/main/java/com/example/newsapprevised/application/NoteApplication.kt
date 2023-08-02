package com.example.newsapprevised.application

import android.app.Application
import com.example.newsapprevised.api.NewsService
import com.example.newsapprevised.api.RetrofitHelper
import com.example.newsapprevised.repository.NewsRepository

class NoteApplication: Application() {
     lateinit var repository:NewsRepository
    override fun onCreate() {
        super.onCreate()
        val newsService= RetrofitHelper.getInstance().create(NewsService::class.java)
        repository= NewsRepository(newsService)
    }
}