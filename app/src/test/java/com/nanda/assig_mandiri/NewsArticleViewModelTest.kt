package com.nanda.assig_mandiri

import androidx.lifecycle.Observer
import com.nanda.assig_mandiri.base.BaseTest
import io.mockk.mockk
import com.nanda.assig_mandiri.feature.article.NewsArticleViewModel
import com.nanda.assig_mandiri.model.DisplayStateArticle
import com.nanda.assig_mandiri.util.CHILD_INDEX_LOADING
import com.nanda.assig_mandiri.util.CHILD_INDEX_SUCCESS
import com.nanda.assig_mandiri.util.CHILD_INDEX_WARNING
import com.nanda.assig_mandiri.util.EMPTY_DATA
import com.nanda.assig_mandiri.util.ERROR
import com.nanda.domain.usecase.model.ArticleItemUiState
import com.nanda.domain.usecase.model.ArticleUiState
import com.nanda.domain.usecase.resource.DataState
import io.mockk.every
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsArticleViewModelTest : BaseTest() {

    lateinit var viewModel: NewsArticleViewModel

    override fun setup() {
        super.setup()
        viewModel = NewsArticleViewModel(newsUseCase)
    }

    @Test
    fun `fetch news article loading init`() {
        // Given
        val source = "bloomberg"
        val query = ""
        val page = 1

        val displayStateObserver = mockk<Observer<DisplayStateArticle?>>(relaxed = true)

        viewModel.displayState.observeForever(displayStateObserver)

        val flowResult = flow { emit(DataState.Loading(null)) }
        every { newsUseCase.getArticle(source, query, page) } returns flowResult

        // When
        viewModel.fetchNewsArticle(source)

        // Then
        verify {
            displayStateObserver.onChanged(viewModel.displayState.value)
        }
        Assert.assertEquals(DisplayStateArticle(CHILD_INDEX_LOADING), viewModel.displayState.value)
    }

    @Test
    fun `fetch news article error - from API`() {
        // Given
        val source = "bloomberg"
        val query = ""
        val errorMessage = "something wrong"
        val page = 1
        val mockDisplayState = DisplayStateArticle(CHILD_INDEX_WARNING, ERROR, errorMessage)

        val displayStateObserver = mockk<Observer<DisplayStateArticle?>>(relaxed = true)

        viewModel.displayState.observeForever(displayStateObserver)

        val flowResult = flow { emit(DataState.Failure<ArticleUiState>(errorMessage)) }
        every { newsUseCase.getArticle(source, query, page) } returns flowResult

        // When
        viewModel.fetchNewsArticle(source)

        // Then
        verifyOrder {
            displayStateObserver.onChanged(viewModel.displayState.value)
        }
        Assert.assertEquals(mockDisplayState, viewModel.displayState.value)
    }

    @Test
    fun `fetch news article - success data empty`() {
        // Given
        val source = "bloomberg"
        val query = ""
        val page = 1
        val mockDisplayState = DisplayStateArticle(CHILD_INDEX_WARNING, EMPTY_DATA)
        val mockArticleData = ArticleUiState(
            total = 0,
            articles = emptyList()
        )

        val displayStateObserver = mockk<Observer<DisplayStateArticle?>>(relaxed = true)
        val newsArticleObserver = mockk<Observer<ArticleUiState?>>(relaxed = true)

        viewModel.displayState.observeForever(displayStateObserver)
        viewModel.newsArticleLiveData.observeForever(newsArticleObserver)

        val flowResult = flow { emit(DataState.Success<ArticleUiState>(mockArticleData)) }
        every { newsUseCase.getArticle(source, query, page) } returns flowResult

        // When
        viewModel.fetchNewsArticle(source)

        // Then
        verifyOrder {
            displayStateObserver.onChanged(viewModel.displayState.value)
            newsArticleObserver.onChanged(viewModel.newsArticleLiveData.value)
        }

        Assert.assertEquals(mockDisplayState, viewModel.displayState.value)
        Assert.assertEquals(mockArticleData, viewModel.newsArticleLiveData.value)
    }

    @Test
    fun `fetch news article - success data from API`() {
        // Given
        val source = "bloomberg"
        val query = ""
        val page = 1
        val mockDisplayState = DisplayStateArticle(CHILD_INDEX_SUCCESS)
        val mockArticleData = ArticleUiState(
            total = 16,
            articles = listOf(
                ArticleItemUiState(
                    id = "bloomberg",
                    title = "title",
                    description = "description",
                    url = "www.sample.com",
                    urlToImage = "url-to-image"
                )
            )
        )

        val displayStateObserver = mockk<Observer<DisplayStateArticle?>>(relaxed = true)
        val newsArticleObserver = mockk<Observer<ArticleUiState?>>(relaxed = true)

        viewModel.displayState.observeForever(displayStateObserver)
        viewModel.newsArticleLiveData.observeForever(newsArticleObserver)

        val flowResult = flow { emit(DataState.Success<ArticleUiState>(mockArticleData)) }
        every { newsUseCase.getArticle(source, query, page) } returns flowResult

        // When
        viewModel.fetchNewsArticle(source)

        // Then
        verifyOrder {
            displayStateObserver.onChanged(viewModel.displayState.value)
            newsArticleObserver.onChanged(viewModel.newsArticleLiveData.value)
        }

        Assert.assertEquals(mockDisplayState, viewModel.displayState.value)
        Assert.assertEquals(mockArticleData, viewModel.newsArticleLiveData.value)
    }

    @Test
    fun `fetch news article - success data load more from API`() {
        // Given
        val source = "bloomberg"
        val query = ""
        val page = 2
        val mockDisplayState = DisplayStateArticle(CHILD_INDEX_SUCCESS)
        val mockArticleData = ArticleUiState(
            total = 16,
            articles = listOf(
                ArticleItemUiState(
                    id = "bloomberg",
                    title = "title",
                    description = "description",
                    url = "www.sample.com",
                    urlToImage = "url-to-image"
                )
            )
        )

        val displayStateObserver = mockk<Observer<DisplayStateArticle?>>(relaxed = true)
        val newsArticleLoadMoreObserver = mockk<Observer<List<ArticleItemUiState>?>>(relaxed = true)

        viewModel.displayState.observeForever(displayStateObserver)
        viewModel.newsArticleLoadMoreLiveData.observeForever(newsArticleLoadMoreObserver)

        val flowResult = flow { emit(DataState.Success<ArticleUiState>(mockArticleData)) }
        every { newsUseCase.getArticle(source, query, page) } returns flowResult

        // When
        viewModel.setCurrentPage(2)
        viewModel.fetchNewsArticle(source, page)

        // Then
        verifyOrder {
            displayStateObserver.onChanged(viewModel.displayState.value)
            newsArticleLoadMoreObserver.onChanged(viewModel.newsArticleLoadMoreLiveData.value)
        }

        Assert.assertEquals(mockDisplayState, viewModel.displayState.value)
        Assert.assertEquals(mockArticleData.articles, viewModel.newsArticleLoadMoreLiveData.value)
    }

    @Test
    fun `fetch news article with search - success data from API`() {
        // Given
        val source = "bloomberg"
        val query = "chatgpt"
        val page = 1
        val mockDisplayState = DisplayStateArticle(CHILD_INDEX_SUCCESS)
        val mockArticleData = ArticleUiState(
            total = 16,
            articles = listOf(
                ArticleItemUiState(
                    id = "bloomberg",
                    title = "title",
                    description = "description",
                    url = "www.sample.com",
                    urlToImage = "url-to-image"
                )
            )
        )

        val displayStateObserver = mockk<Observer<DisplayStateArticle?>>(relaxed = true)
        val newsArticleObserver = mockk<Observer<ArticleUiState?>>(relaxed = true)

        viewModel.setQuery(query)
        viewModel.displayState.observeForever(displayStateObserver)
        viewModel.newsArticleLiveData.observeForever(newsArticleObserver)

        val flowResult = flow { emit(DataState.Success<ArticleUiState>(mockArticleData)) }
        every { newsUseCase.getArticle(source, query, page) } returns flowResult

        // When
        viewModel.fetchNewsArticle(source)

        // Then
        verifyOrder {
            displayStateObserver.onChanged(viewModel.displayState.value)
            newsArticleObserver.onChanged(viewModel.newsArticleLiveData.value)
        }

        Assert.assertEquals(mockDisplayState, viewModel.displayState.value)
        Assert.assertEquals(mockArticleData, viewModel.newsArticleLiveData.value)
    }
}