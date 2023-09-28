package com.nanda.assig_mandiri.model

import com.nanda.assig_mandiri.R

enum class CategoryType(val value: String, val title: String, val icon: Int) {
    BUSINESS("business", "Business", R.drawable.ic_business),
    ENTERTAINMENT("entertainment", "Entertainment", R.drawable.ic_entertainment),
    GENERAL("general", "General", R.drawable.ic_general),
    HEALTH("health", "Health", R.drawable.ic_health),
    SCIENCE("science", "Science", R.drawable.ic_science),
    SPORTS("sports", "Sports", R.drawable.ic_sports),
    TECHNOLOGY("technology", "Technology", R.drawable.ic_technology)
}