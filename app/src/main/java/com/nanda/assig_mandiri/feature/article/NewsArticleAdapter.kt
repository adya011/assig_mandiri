package com.nanda.assig_mandiri.feature.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nanda.assig_mandiri.R
import com.nanda.assig_mandiri.databinding.ItemArticleBinding
import com.nanda.assig_mandiri.databinding.ItemLoadingBinding
import com.nanda.assig_mandiri.util.loadImage
import com.nanda.domain.usecase.model.ArticleItemUiState

class NewsArticleAdapter(val onCLick: (String) -> Unit) :
    ListAdapter<ArticleItemUiState, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun getItemViewType(position: Int): Int =
        if (getItem(position).isLoading.not()) {
            R.layout.item_article
        } else {
            R.layout.item_loading
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_article -> {
                val binding = ItemArticleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                NewsArticleViewHolder(binding)
            }

            else -> {
                val binding = ItemLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                LoadingViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_article -> {
                (holder as NewsArticleViewHolder).bind(getItem(position))
            }

            else -> {
                (holder as LoadingViewHolder)
            }
        }
    }

    inner class NewsArticleViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticleItemUiState) = with(binding) {
            tvTitle.text = item.title
            tvDescription.text = item.description
            ivImage.loadImage(
                itemView.context,
                item.urlToImage,
                R.drawable.bg_placeholder
            )
            root.setOnClickListener {
                onCLick.invoke(item.url)
            }
        }
    }

    class LoadingViewHolder(
        private val binding: ItemLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ArticleItemUiState>() {
            override fun areItemsTheSame(
                oldItem: ArticleItemUiState,
                newItem: ArticleItemUiState
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ArticleItemUiState,
                newItem: ArticleItemUiState
            ): Boolean = oldItem == newItem
        }
    }
}