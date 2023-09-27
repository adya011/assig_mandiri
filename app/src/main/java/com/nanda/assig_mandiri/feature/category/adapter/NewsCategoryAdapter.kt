package com.nanda.assig_mandiri.feature.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nanda.assig_mandiri.databinding.ItemCategoryBinding
import com.nanda.assig_mandiri.model.NewsCategoryUiState

class NewsCategoryAdapter : ListAdapter<NewsCategoryUiState, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return NewsCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NewsCategoryViewHolder).bind(getItem(position))
    }

    class NewsCategoryViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsCategoryUiState) = with(binding) {
            tvCategoryName.text = item.name
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<NewsCategoryUiState>() {
            override fun areItemsTheSame(
                oldItem: NewsCategoryUiState,
                newItem: NewsCategoryUiState
            ): Boolean = oldItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: NewsCategoryUiState,
                newItem: NewsCategoryUiState
            ): Boolean = oldItem == newItem

        }
    }
}