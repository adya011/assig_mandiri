package com.nanda.assig_mandiri.feature.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nanda.assig_mandiri.R
import com.nanda.assig_mandiri.databinding.ItemArticleBinding
import com.nanda.assig_mandiri.util.loadImage
import com.nanda.domain.usecase.model.ArticleUiState

class NewsArticleAdapter(val onCLick: (String) -> Unit) :
    ListAdapter<ArticleUiState, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return NewsArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NewsArticleViewHolder).bind(getItem(position))
    }

    inner class NewsArticleViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticleUiState) = with(binding) {
            tvTitle.text = item.title
            tvDescription.text = item.description
            ivImage.loadImage(
                itemView.context,
                item.urlToImage,
                R.drawable.bg_placeholder
            )
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ArticleUiState>() {
            override fun areItemsTheSame(
                oldItem: ArticleUiState,
                newItem: ArticleUiState
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ArticleUiState,
                newItem: ArticleUiState
            ): Boolean = oldItem == newItem
        }
    }
}