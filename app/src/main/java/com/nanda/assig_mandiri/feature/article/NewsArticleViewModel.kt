package com.nanda.assig_mandiri.feature.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanda.assig_mandiri.util.CHILD_INDEX_ERROR
import com.nanda.assig_mandiri.util.CHILD_INDEX_LOADING
import com.nanda.assig_mandiri.util.CHILD_INDEX_SUCCESS
import com.nanda.domain.usecase.NewsArticleUseCase
import com.nanda.domain.usecase.model.ArticleUiState
import com.nanda.domain.usecase.resource.DataState
import kotlinx.coroutines.launch

class NewsArticleViewModel(
    private val newsArticleUseCase: NewsArticleUseCase
) : ViewModel() {

    private val _newsArticleLiveData by lazy { MutableLiveData<List<ArticleUiState>>() }
    val newsArticleLiveData: LiveData<List<ArticleUiState>> get() = _newsArticleLiveData

    private val _displayChild: MutableLiveData<Int> = MutableLiveData()
    val displayChild get() = _displayChild as LiveData<Int>

    fun fetchNewsArticle(source: String) {
        viewModelScope.launch {
            newsArticleUseCase.getArticle(source).collect { result ->
                when (result) {
                    is DataState.Loading -> {
                        _displayChild.value = CHILD_INDEX_LOADING
                    }

                    is DataState.Success -> {
                        _newsArticleLiveData.value = result.data
                        _displayChild.value = CHILD_INDEX_SUCCESS
                    }

                    is DataState.Failure -> {
                        _displayChild.value = CHILD_INDEX_ERROR
                    }
                }
            }
        }
    }
}