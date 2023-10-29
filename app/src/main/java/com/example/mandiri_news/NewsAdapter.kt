package com.example.mandiri_news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class NewsAdapter(
    private val newsArticles: List<berita>,
    private val beritaTerkini: berita,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_BERITA_TERKINI = 0
    private val VIEW_TYPE_SEMUA_BERITA = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_BERITA_TERKINI) {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.berita_terkini, parent, false)
            BeritaTerkiniViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_item, parent, false)
            NewsViewHolder(itemView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_BERITA_TERKINI
        } else {
            VIEW_TYPE_SEMUA_BERITA
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_BERITA_TERKINI) {
            val beritaTerkiniHolder = holder as BeritaTerkiniViewHolder
            beritaTerkiniHolder.bindData(beritaTerkini)
        } else {
            val newsViewHolder = holder as NewsViewHolder
            val currentArticle = newsArticles[position - 1]
            newsViewHolder.bindData(currentArticle)

            newsViewHolder.itemView.setOnClickListener {
                onItemClick(currentArticle.url)
            }
        }
    }


    override fun getItemCount(): Int {
        return newsArticles.size + 1
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.jdlAPI)
        private val tglTextView: TextView = itemView.findViewById(R.id.tglAPI)
        private val imageView: ImageView = itemView.findViewById(R.id.imgAPI)

        fun bindData(currentArticle: berita) {
            titleTextView.text = currentArticle.title
            tglTextView.text = currentArticle.publishedAt

            Picasso.get()
                .load(currentArticle.urlToImage)
                .error(R.drawable.loading)
                .into(imageView, object: Callback {
                    override fun onSuccess() {
                    }

                    override fun onError(e: Exception?) {
                    }
                })
        }
    }

    inner class BeritaTerkiniViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextViewUp: TextView = itemView.findViewById(R.id.JudulBerita)
        private val imageViewUp: ImageView = itemView.findViewById(R.id.imageBerita)
        private val publishedAt: TextView = itemView.findViewById(R.id.tglBerita)

        fun bindData(beritaTerkini: berita) {
            titleTextViewUp.text = beritaTerkini.title
            publishedAt.text = beritaTerkini.publishedAt

            Picasso.get()
                .load(beritaTerkini.urlToImage)
                .error(R.drawable.loading)
                .into(imageViewUp, object: Callback {
                    override fun onSuccess() {
                    }

                    override fun onError(e: Exception?) {
                    }
                })
        }
    }
}
