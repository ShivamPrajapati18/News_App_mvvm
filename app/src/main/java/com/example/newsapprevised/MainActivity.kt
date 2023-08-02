package com.example.newsapprevised

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapprevised.adapter.ItemClicked
import com.example.newsapprevised.adapter.NewsAdapter
import com.example.newsapprevised.application.NoteApplication
import com.example.newsapprevised.databinding.ActivityMainBinding
import com.example.newsapprevised.model.Article
import com.example.newsapprevised.repository.NewsRepository
import com.example.newsapprevised.viewmodel.NewsViewModel
import com.example.newsapprevised.viewmodel.NewsViewModelFactory
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), ItemClicked, SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsRepository: NewsRepository
    private lateinit var mAdapter:NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsRepository=(application as NoteApplication).repository
        newsViewModel=ViewModelProvider(this,NewsViewModelFactory(newsRepository))[NewsViewModel::class.java]
        mAdapter= NewsAdapter(this)
        binding.recylerView.adapter=mAdapter
        binding.recylerView.layoutManager=LinearLayoutManager(this)

        setSupportActionBar(binding.toolbar)
        /*Initial News Displaying*/
        getNewsData("in","business")
        /*Handling the Bottom Navigation*/
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.business->{
                    getNewsData("in","business")
                }
                R.id.science->{
                    getNewsData("in","science")
                }
                R.id.sports->{
                    getNewsData("in","sports")
                }
                R.id.technology->{
                    getNewsData("in","technology")
                }
                R.id.health->{
                    getNewsData("in","health")
                }
            }
            true
        }
    }

    private fun getNewsData(country: String, category: String) {
        newsViewModel.getNews(country,category)
        newsViewModel.newsData.observe(this@MainActivity){
            it?.let {
                val article=it.articles as ArrayList<Article>
                mAdapter.updatedItem(article)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        /*Type casting view in SearchView*/
        val item = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView
        item.queryHint = "Enter The Text"
        item.setOnQueryTextListener(this)
        return true
    }
    /*Handling the clicked the news*/
    override fun newsClicked(list: Article) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(list.url))
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!=null){
            newsViewModel.getNewsBySearch(query,"en")
            newsViewModel.newsData.observe(this){
                it?.let {
                    val article = it.articles as ArrayList<Article>
                    mAdapter.updatedItem(article)
                }
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}

