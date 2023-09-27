package com.nanda.assig_mandiri.feature.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanda.domain.usecase.NewsArticleUseCase
import com.nanda.domain.usecase.model.SourceUiState
import com.nanda.domain.usecase.resource.DataState
import kotlinx.coroutines.launch

class NewsSourceViewModel(
    private val newsArticleUseCase: NewsArticleUseCase
) : ViewModel() {

    private val _newsSourceData by lazy { MutableLiveData<List<SourceUiState>>() }
    val newsSourceData: LiveData<List<SourceUiState>> get() = _newsSourceData

    fun fetchNewsSource(category: String) {
        viewModelScope.launch {
            newsArticleUseCase.getSources(category).collect { result ->
                when (result) {
                    is DataState.Loading -> {

                    }

                    is DataState.Success -> {
                        _newsSourceData.value = result.data
                    }

                    is DataState.Failure -> {

                    }
                }
            }
        }
    }
}