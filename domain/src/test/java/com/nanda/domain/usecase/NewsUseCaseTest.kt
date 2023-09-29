package com.nanda.domain.usecase

import app.cash.turbine.test
import com.nanda.domain.base.BaseTest
import com.nanda.domain.usecase.model.ArticleItemUiState
import com.nanda.domain.usecase.model.ArticleUiState
import com.nanda.domain.usecase.model.SourceUiState
import com.nanda.domain.usecase.resource.DataState
import com.nanda.repository.model.ArticleEntity
import com.nanda.repository.model.ArticleItemEntity
import com.nanda.repository.model.DataResult
import com.nanda.repository.model.SourceEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import org.junit.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsUseCaseTest : BaseTest() {

    private lateinit var useCase: NewsUseCase

    override fun setup() {
        super.setup()
        useCase = NewsUseCase(newsRepository, appDispatchers)
    }

    @Test
    fun `Get news article success - return article data`() = runTest {
        // Given
        val source = "bloomberg"
        val query = ""
        val page = 1

        coEvery {
            newsRepository.getArticle(source, query, page)
        } returns DataResult.Success(
            ArticleEntity(
                total = 16,
                articles = listOf(
                    ArticleItemEntity(
                        id = "",
                        title = "",
                        description = "",
                        url = "",
                        urlToImage = ""
                    )
                )
            )
        )

        // When
        useCase.getArticle(source, query, page).test {
            // Then
            assertEquals(
                DataState.Loading(null),
                awaitItem()
            )

            assertEquals(
                DataState.Success(
                    ArticleUiState(
                        total = 16,
                        articles = listOf(
                            ArticleItemUiState(
                                id = "",
                                title = "",
                                description = "",
                                url = "",
                                urlToImage = ""
                            )
                        )
                    )
                ),
                awaitItem()
            )

            awaitComplete()
        }

        coVerify {
            newsRepository.getArticle(source, query, page)
        }

        confirmVerified(newsRepository)
    }

    @Test
    fun `Get news source success - return source data`() = runTest {
        // Given
        val category = "business"

        coEvery {
            newsRepository.getSource(category)
        } returns DataResult.Success(
            listOf(
                SourceEntity(
                    id = "",
                    name = "",
                    description = "",
                    url = ""
                )
            )
        )

        // When
        useCase.getSources(category).test {
            // Then
            assertEquals(
                DataState.Loading(null),
                awaitItem()
            )

            assertEquals(
                DataState.Success(
                    listOf(
                        SourceUiState(
                            id = "",
                            name = "",
                            description = "",
                            url = ""
                        )
                    )
                ),
                awaitItem()
            )

            awaitComplete()
        }

        coVerify {
            newsRepository.getSource(category)
        }

        confirmVerified(newsRepository)
    }

    @Test
    fun `Get article error - return error`() = runTest {
        val source = "bloomberg"
        val query = ""
        val page = 1
        val mockErrorMessage = "system error"
        val mockErrorCode = 500

        // Given
        coEvery {
            newsRepository.getArticle(source, query, page)
        } returns DataResult.Error(mockErrorMessage, mockErrorCode)

        // When
        useCase.getArticle(source, query, page).test {
            // Then
            assertEquals(
                DataState.Loading(null),
                awaitItem()
            )

            assertEquals(
                DataState.Failure<Any>(mockErrorMessage),
                awaitItem()
            )

            awaitComplete()
        }

        coVerify {
            newsRepository.getArticle(source, query, page)
        }

        confirmVerified(newsRepository)
    }

    @Test
    fun `Get source error - return error`() = runTest {
        val category = "business"
        val mockErrorMessage = "system error"
        val mockErrorCode = 500

        // Given
        coEvery {
            newsRepository.getSource(category)
        } returns DataResult.Error(mockErrorMessage, mockErrorCode)

        // When
        useCase.getSources(category).test {
            // Then
            assertEquals(
                DataState.Loading(null),
                awaitItem()
            )

            assertEquals(
                DataState.Failure<Any>(mockErrorMessage),
                awaitItem()
            )

            awaitComplete()
        }

        coVerify {
            newsRepository.getSource(category)
        }

        confirmVerified(newsRepository)
    }
}