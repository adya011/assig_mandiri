package com.nanda.assig_mandiri.feature.article

import androidx.lifecycle.ViewModel
import com.nanda.domain.usecase.NewsArticleUseCase

class NewsArticleViewModel(
    private val newsArticleUseCase: NewsArticleUseCase
) : ViewModel() {

}