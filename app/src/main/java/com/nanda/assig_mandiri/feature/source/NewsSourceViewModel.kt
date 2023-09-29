package com.nanda.assig_mandiri.feature.source

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
import com.nanda.domain.usecase.NewsUseCase
import com.nanda.domain.usecase.model.SourceUiState
import com.nanda.domain.usecase.resource.DataState
import kotlinx.coroutines.launch

class NewsSourceViewModel(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    private val _newsSourceLiveData by lazy { MutableLiveData<List<SourceUiState>>() }
    val newsSourceLiveData: LiveData<List<SourceUiState>> get() = _newsSourceLiveData

    private val _displayState: MutableLiveData<DisplayStateArticle> = MutableLiveData()
    val displayState get() = _displayState as LiveData<DisplayStateArticle>

    fun fetchNewsSource(category: String) {
        viewModelScope.launch {
            newsUseCase.getSources(category).collect { result ->
                when (result) {
                    is DataState.Loading -> {
                        _displayState.value = DisplayStateArticle(CHILD_INDEX_LOADING)
                    }

                    is DataState.Success -> {
                        if (result.data.isEmpty().not()) {
                            _displayState.value = DisplayStateArticle(CHILD_INDEX_SUCCESS)
                        } else {
                            _displayState.value =
                                DisplayStateArticle(CHILD_INDEX_WARNING, EMPTY_DATA)
                        }

                        _newsSourceLiveData.value = result.data
                    }

                    is DataState.Failure -> {
                        _displayState.value =
                            DisplayStateArticle(CHILD_INDEX_WARNING, ERROR, result.errorMessage)
                    }
                }
            }
        }
    }
}