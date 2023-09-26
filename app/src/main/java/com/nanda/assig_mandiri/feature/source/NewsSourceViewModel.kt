package com.nanda.assig_mandiri.feature.source

import androidx.lifecycle.ViewModel
import com.nanda.domain.usecase.NewsUseCase

class NewsSourceViewModel(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

}