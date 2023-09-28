package com.nanda.assig_mandiri.feature.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nanda.assig_mandiri.databinding.ItemCategoryBinding
import com.nanda.assig_mandiri.model.CategoryType

class NewsCategoryAdapter(val onClick: (CategoryType) -> Unit) :
    ListAdapter<CategoryType, RecyclerView.ViewHolder>(COMPARATOR) {

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

    inner class NewsCategoryViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryType) = with(binding) {
            ivCategory.setImageResource(item.icon)
            tvCategoryName.text = item.title
            root.setOnClickListener {
                onClick.invoke(item)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<CategoryType>() {
            override fun areItemsTheSame(
                oldItem: CategoryType,
                newItem: CategoryType
            ): Boolean = oldItem.value == newItem.value

            override fun areContentsTheSame(
                oldItem: CategoryType,
                newItem: CategoryType
            ): Boolean = oldItem == newItem

        }
    }
}
