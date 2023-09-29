package com.nanda.assig_mandiri.feature.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanda.assig_mandiri.model.DisplayStateArticle
import com.nanda.assig_mandiri.util.CHILD_INDEX_WARNING
import com.nanda.assig_mandiri.util.CHILD_INDEX_LOADING
import com.nanda.assig_mandiri.util.CHILD_INDEX_SUCCESS
import com.nanda.assig_mandiri.util.EMPTY_DATA
import com.nanda.assig_mandiri.util.ERROR
import com.nanda.domain.usecase.NewsArticleUseCase
import com.nanda.domain.usecase.model.ArticleItemUiState
import com.nanda.domain.usecase.model.ArticleUiState
import com.nanda.domain.usecase.resource.DataState
import kotlinx.coroutines.launch

class NewsArticleViewModel(
    private val newsArticleUseCase: NewsArticleUseCase
) : ViewModel() {

    private val _newsArticleLiveData by lazy { MutableLiveData<ArticleUiState>() }
    val newsArticleLiveData: LiveData<ArticleUiState> get() = _newsArticleLiveData

    private val _newsArticleLoadMoreLiveData by lazy { MutableLiveData<List<ArticleItemUiState>>() }
    val newsArticleLoadMoreLiveData: LiveData<List<ArticleItemUiState>> get() = _newsArticleLoadMoreLiveData

    private val _displayState: MutableLiveData<DisplayStateArticle> = MutableLiveData()
    val displayState get() = _displayState as LiveData<DisplayStateArticle>

    private var query = ""
    private var currentPage: Int = 1

    fun fetchNewsArticle(source: String, page: Int = 1) {
        viewModelScope.launch {
            newsArticleUseCase.getArticle(source, query, page).collect { result ->
                when (result) {
                    is DataState.Loading -> {
                        if (currentPage == 1) {
                            _displayState.value = DisplayStateArticle(CHILD_INDEX_LOADING)
                        }
                    }

                    is DataState.Success -> {
                        if(result.data.articles.isEmpty().not()) {
                            _displayState.value = DisplayStateArticle(CHILD_INDEX_SUCCESS)
                        } else {
                            _displayState.value = DisplayStateArticle(CHILD_INDEX_WARNING, EMPTY_DATA)
                        }

                        if (currentPage == 1) {
                            _newsArticleLiveData.value = result.data
                        } else {
                            _newsArticleLoadMoreLiveData.value = result.data.articles
                        }
                    }

                    is DataState.Failure -> {
                        _displayState.value =
                            DisplayStateArticle(CHILD_INDEX_WARNING, ERROR, result.errorMessage)
                    }
                }
            }
        }
    }

    fun setQuery(text: String) {
        query = text
    }

    fun clearQuery() {
        query = ""
    }

    fun getQuery() = query

    fun getCurrentPage() = currentPage

    fun addCurrentPage() {
        currentPage++
    }

    fun resetCurrentPage() {
        currentPage = 1
    }
}