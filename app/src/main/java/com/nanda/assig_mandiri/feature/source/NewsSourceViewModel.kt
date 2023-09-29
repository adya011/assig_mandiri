package com.nanda.assig_mandiri.feature.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanda.assig_mandiri.util.CHILD_INDEX_WARNING
import com.nanda.assig_mandiri.util.CHILD_INDEX_LOADING
import com.nanda.assig_mandiri.util.CHILD_INDEX_SUCCESS
import com.nanda.domain.usecase.NewsUseCase
import com.nanda.domain.usecase.model.SourceUiState
import com.nanda.domain.usecase.resource.DataState
import kotlinx.coroutines.launch

class NewsSourceViewModel(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    private val _newsSourceLiveData by lazy { MutableLiveData<List<SourceUiState>>() }
    val newsSourceLiveData: LiveData<List<SourceUiState>> get() = _newsSourceLiveData

    private val _displayChild: MutableLiveData<Pair<Int, String>> = MutableLiveData()
    val displayChild get() = _displayChild as LiveData<Pair<Int, String>>

    fun fetchNewsSource(category: String) {
        viewModelScope.launch {
            newsUseCase.getSources(category).collect { result ->
                when (result) {
                    is DataState.Loading -> {
                        _displayChild.value = CHILD_INDEX_LOADING to ""
                    }

                    is DataState.Success -> {
                        _newsSourceLiveData.value = result.data
                        _displayChild.value = CHILD_INDEX_SUCCESS to ""
                    }

                    is DataState.Failure -> {
                        _displayChild.value = CHILD_INDEX_WARNING to result.errorMessage
                    }
                }
            }
        }
    }
}