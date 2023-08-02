package com.example.newsapprevised.api

import com.example.newsapprevised.model.NewsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY="51f04c5c1a2d40f18d15ce6dc2933600"
interface NewsService {
    @GET("v2/top-headlines?apiKey=$API_KEY")
    suspend fun getNews(@Query("country") country:String,@Query("category")category:String):Response<NewsModel>

    @GET("v2/everything?apiKey=$API_KEY")
    suspend fun getNewsBySearch(@Query("q")query:String,@Query("language")language:String):Response<NewsModel>
}