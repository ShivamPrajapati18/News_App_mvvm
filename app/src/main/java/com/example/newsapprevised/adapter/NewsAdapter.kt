package com.example.newsapprevised.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapprevised.R
import com.example.newsapprevised.model.Article

class NewsAdapter(val listner:ItemClicked) :RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

    private val list:ArrayList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.news_rv_sample,parent,false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem=list[position]
        holder.author.text=currentItem.author
        holder.title.text=currentItem.title
        Glide.with(holder.itemView.context).load(currentItem.urlToImage).into(holder.image)
        holder.itemView.setOnClickListener {
            listner.newsClicked(currentItem)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatedItem(article: java.util.ArrayList<Article>) {
        list.clear()
        list.addAll(article)
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title:TextView=itemView.findViewById(R.id.Title)
        var author:TextView=itemView.findViewById(R.id.Author)
        var image:ImageView=itemView.findViewById(R.id.Image)
    }
}

interface ItemClicked{
    fun newsClicked(list:Article)
}