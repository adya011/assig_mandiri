package com.nanda.assig_mandiri.feature.source

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nanda.assig_mandiri.databinding.ItemSourceBinding
import com.nanda.domain.usecase.model.SourceUiState

class NewsSourceAdapter(val onClick: (String, String) -> Unit) :
    ListAdapter<SourceUiState, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemSourceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return NewsSourceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NewsSourceViewHolder).bind(getItem(position))
    }

    inner class NewsSourceViewHolder(
        private val binding: ItemSourceBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SourceUiState) = with(binding) {
            tvTitle.text = item.name
            tvSource.text = item.url
            tvDescription.text = item.description
            root.setOnClickListener {
                onClick.invoke(item.id, item.name)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<SourceUiState>() {
            override fun areItemsTheSame(oldItem: SourceUiState, newItem: SourceUiState): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: SourceUiState,
                newItem: SourceUiState
            ): Boolean = oldItem == newItem

        }
    }
}