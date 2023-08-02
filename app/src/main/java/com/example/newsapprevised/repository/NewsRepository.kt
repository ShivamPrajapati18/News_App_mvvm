package com.example.newsapprevised.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapprevised.api.NewsService
import com.example.newsapprevised.model.NewsModel
import retrofit2.http.Query

class NewsRepository(private val newsService: NewsService) {

    private val newsDataMutableLiveData= MutableLiveData<NewsModel>()
    val newsData:LiveData<NewsModel>
    get() = newsDataMutableLiveData

    suspend fun getNews(country:String,category:String){
        val result=newsService.getNews(country, category)
        result.body()?.let {
            newsDataMutableLiveData.postValue(it)
        }
    }

    suspend fun getNewsBySearch(query:String,language:String) {
        val result=newsService.getNewsBySearch(query, language)
        result.body()?.let {
            newsDataMutableLiveData.postValue(it)
        }
    }
}