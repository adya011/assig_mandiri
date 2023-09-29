package com.nanda.domain.usecase

import com.nanda.domain.model.resource.AppDispatchers
import com.nanda.domain.usecase.resource.DataState
import com.nanda.domain.usecase.model.ArticleItemUiState
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
    fun getArticle(source: String, query: String, page: Int) = flow {
        emit(DataState.Loading(null))

        when (val response = newsRepository.getArticle(source, query, page)) {
            is DataResult.Success -> {
                val data = response.body
                emit(DataState.Success(data.mapNewsArticle()))
            }

            is DataResult.Error -> {
                emit(DataState.Failure(response.message))
            }
        }
    }.flowOn(appDispatchers.io)

    private fun ArticleEntity.mapNewsArticle(): ArticleUiState {
        return ArticleUiState(
            total = total,
            articles = articles.map {
                ArticleItemUiState(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    url = it.url,
                    urlToImage = it.urlToImage
                )
            }
        )
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