package com.nanda.assig_mandiri.feature.article

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanda.domain.usecase.NewsArticleUseCase
import com.nanda.domain.usecase.model.ArticleUiState
import com.nanda.domain.usecase.resource.DataState
import kotlinx.coroutines.launch

class NewsArticleViewModel(
    private val newsArticleUseCase: NewsArticleUseCase
) : ViewModel() {

    private val _newsArticleLiveData by lazy { MutableLiveData<List<ArticleUiState>>() }
    val newsArticleLiveData: LiveData<List<ArticleUiState>> get() = _newsArticleLiveData

    fun fetchNewsArticle(source: String) {
        viewModelScope.launch {
            newsArticleUseCase.getArticle(source).collect { result ->
                when (result) {
                    is DataState.Loading -> {
                        Log.d("nandaDebug", "news article loading")
                    }

                    is DataState.Success -> {
                        Log.d("nandaDebug", "news article success")
                        _newsArticleLiveData.value = result.data
                    }

                    is DataState.Failure -> {
                        Log.d("nandaDebug", "news article failure: ${result.errorMessage}")
                    }
                }
            }
        }
    }
}