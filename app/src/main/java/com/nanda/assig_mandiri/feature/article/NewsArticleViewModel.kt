package com.nanda.assig_mandiri.feature.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanda.assig_mandiri.util.CHILD_INDEX_ERROR
import com.nanda.assig_mandiri.util.CHILD_INDEX_LOADING
import com.nanda.assig_mandiri.util.CHILD_INDEX_SUCCESS
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

    private val _displayChild: MutableLiveData<Pair<Int, String>> = MutableLiveData()
    val displayChild get() = _displayChild as LiveData<Pair<Int, String>>

    private var query = ""
    private var currentPage: Int = 1

    fun fetchNewsArticle(source: String, page: Int = 1) {
        viewModelScope.launch {
            newsArticleUseCase.getArticle(source, query, page).collect { result ->
                when (result) {
                    is DataState.Loading -> {
                        if (currentPage == 1) {
                            _displayChild.value = CHILD_INDEX_LOADING to ""
                        }
                    }

                    is DataState.Success -> {
                        _displayChild.value = CHILD_INDEX_SUCCESS to ""

                        if (currentPage == 1) {
                            _newsArticleLiveData.value = result.data
                        } else {
                            _newsArticleLoadMoreLiveData.value = result.data.articles
                        }
                    }

                    is DataState.Failure -> {
                        _displayChild.value = CHILD_INDEX_ERROR to result.errorMessage
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