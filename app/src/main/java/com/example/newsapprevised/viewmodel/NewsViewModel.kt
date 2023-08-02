package com.example.newsapprevised.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapprevised.model.NewsModel
import com.example.newsapprevised.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository):ViewModel() {

    val newsData: LiveData<NewsModel> = newsRepository.newsData
    fun getNews(country: String, category: String) {
        viewModelScope.launch {
            newsRepository.getNews(country, category)
        }
    }
    fun getNewsBySearch(query:String,language:String) {
        viewModelScope.launch {
            newsRepository.getNewsBySearch(query, language)
        }
    }
}