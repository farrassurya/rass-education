package com.example.rass_education.tugas_p11

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rass_education.R
import com.example.rass_education.databinding.ItemNewsBinding
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class NewsAdapter(
    private val onNewsClicked: (NewsArticle) -> Unit
) : ListAdapter<NewsArticle, NewsAdapter.NewsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position), onNewsClicked)
    }

    class NewsViewHolder(
        private val binding: ItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NewsArticle, onNewsClicked: (NewsArticle) -> Unit) {
            binding.tvNewsTitle.text = item.title
            binding.tvNewsSummary.text = item.summary
            binding.tvNewsSource.text = binding.root.context.getString(
                R.string.p11_news_source_format,
                item.newsSite,
                formatPublishedDate(item.publishedAt)
            )
            binding.ivNewsImage.load(item.imageUrl) {
                crossfade(true)
                placeholder(android.R.drawable.ic_menu_report_image)
                error(android.R.drawable.ic_menu_report_image)
            }
            binding.root.setOnClickListener {
                onNewsClicked(item)
            }
        }

        private fun formatPublishedDate(rawDate: String): String {
            return runCatching {
                OffsetDateTime.parse(rawDate)
                    .format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("id", "ID")))
            }.getOrDefault(rawDate)
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<NewsArticle>() {
        override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean =
            oldItem == newItem
    }
}



