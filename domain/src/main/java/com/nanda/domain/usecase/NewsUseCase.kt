package com.nanda.domain.usecase

import com.nanda.domain.model.resource.AppDispatchers
import com.nanda.domain.usecase.resource.DataState
import com.nanda.domain.usecase.model.ArticleUiState
import com.nanda.repository.NewsRepository
import com.nanda.repository.model.ArticleEntity
import com.nanda.repository.model.DataResult
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsUseCase(
    private val newsRepository: NewsRepository,
    private val appDispatchers: AppDispatchers
) {
    fun getArticle() = flow {
        emit(DataState.Loading(null))

        when (val response = newsRepository.getArticle()) {
            is DataResult.Success -> {
                val data = response.body
                emit(DataState.Success(data.mapToPresentation()))
            }

            is DataResult.Error -> {
                emit(DataState.Failure(response.message))
            }
        }
    }.flowOn(appDispatchers.io)

    private fun List<ArticleEntity>.mapToPresentation(): List<ArticleUiState> {
        return map {
            ArticleUiState(
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content
            )
        }
    }
}