package com.nabin0.news.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nabin0.news.data.model.Article
import com.nabin0.news.databinding.NewsListItemBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.MyNewsViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNewsViewHolder {
        val binding = NewsListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyNewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class MyNewsViewHolder(private val binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.newsHeadingTextView.text = article.title
            binding.newsDescriptionTextView.text = article.description
            binding.publishedAtTextView.text = article.publishedAt
            binding.authorTextView.text = article.author
            Glide.with(binding.newsImageView.context).load(article.urlToImage)
                .into(binding.newsImageView)

            binding.root.setOnClickListener{
                onItemCilckListener?.let {
                    it(article)
                }
            }
        }
    }


    private var onItemCilckListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemCilckListener = listener
    }
}