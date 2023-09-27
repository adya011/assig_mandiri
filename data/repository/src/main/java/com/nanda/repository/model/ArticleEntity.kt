package com.nanda.repository.model

data class ArticleEntity(
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String
)