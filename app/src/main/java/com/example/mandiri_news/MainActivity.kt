package com.example.mandiri_news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import android.content.Intent
import android.net.Uri

class MainActivity : AppCompatActivity() {
    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsApiService = retrofit.create(beritaInterface::class.java)

        mainScope.launch(Dispatchers.IO) {
            val response = newsApiService.getTopHeadlines("e8a10397f2fd4e21aaecceef54ca3ee6", "us")

            if (response.isSuccessful) {
                val newsArticles = response.body()?.articles
                val beritaTerkini = newsArticles?.firstOrNull()

                runOnUiThread {
                    val recyclerView = findViewById<RecyclerView>(R.id.berita)
                    val adapter = NewsAdapter(newsArticles ?: emptyList(), beritaTerkini ?: berita("", "", "", "", "")) { url ->
                        openWebPage(url)
                    }
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }
        }
    }

    private fun openWebPage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}