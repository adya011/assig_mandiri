package com.nanda.domain.usecase.model

data class ArticleItemUiState(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String = "",
    var isLoading: Boolean = false
)