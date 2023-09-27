package com.nanda.domain.usecase.model

data class ArticleUiState(
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String
)