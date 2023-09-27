package com.nanda.domain.usecase

import com.nanda.domain.model.resource.AppDispatchers
import com.nanda.domain.usecase.resource.DataState
import com.nanda.domain.usecase.model.ArticleUiState
import com.nanda.domain.usecase.model.SourceUiState
import com.nanda.repository.NewsRepository
import com.nanda.repository.model.ArticleEntity
import com.nanda.repository.model.DataResult
import com.nanda.repository.model.SourceEntity
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsArticleUseCase(
    private val newsRepository: NewsRepository,
    private val appDispatchers: AppDispatchers
) {
    fun getArticle() = flow {
        emit(DataState.Loading(null))

        when (val response = newsRepository.getArticle()) {
            is DataResult.Success -> {
                val data = response.body
                emit(DataState.Success(data.mapNewsArticle()))
            }

            is DataResult.Error -> {
                emit(DataState.Failure(response.message))
            }
        }
    }.flowOn(appDispatchers.io)

    private fun List<ArticleEntity>.mapNewsArticle(): List<ArticleUiState> {
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

    fun getSources(category: String) = flow {
        emit(DataState.Loading(null))

        when (val response = newsRepository.getSource(category)) {
            is DataResult.Success -> {
                val data = response.body
                emit(DataState.Success(data.mapNewsSource()))
            }

            is DataResult.Error -> {
                emit(DataState.Failure(response.message))
            }
        }
    }.flowOn(appDispatchers.io)

    private fun List<SourceEntity>.mapNewsSource(): List<SourceUiState> {
        return map {
            SourceUiState(
                id = it.id,
                name = it.name,
                description = it.description,
                url = it.url
            )
        }
    }
}