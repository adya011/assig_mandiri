package com.nanda.domain.usecase.model

data class ArticleUiState(
    val total: Int = 0,
    val articles: List<ArticleItemUiState> = emptyList()
)