package com.nanda.assig_mandiri.feature.article

import androidx.lifecycle.ViewModel
import com.nanda.domain.usecase.NewsUseCase

class NewsArticleViewModel(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

}