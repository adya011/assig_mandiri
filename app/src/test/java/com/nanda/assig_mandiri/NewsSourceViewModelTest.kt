package com.nanda.assig_mandiri

import androidx.lifecycle.Observer
import com.nanda.assig_mandiri.base.BaseTest
import com.nanda.assig_mandiri.feature.source.NewsSourceViewModel
import com.nanda.assig_mandiri.model.DisplayStateArticle
import com.nanda.assig_mandiri.util.CHILD_INDEX_LOADING
import com.nanda.assig_mandiri.util.CHILD_INDEX_SUCCESS
import com.nanda.assig_mandiri.util.CHILD_INDEX_WARNING
import com.nanda.assig_mandiri.util.EMPTY_DATA
import com.nanda.assig_mandiri.util.ERROR
import com.nanda.domain.usecase.model.SourceUiState
import com.nanda.domain.usecase.resource.DataState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsSourceViewModelTest: BaseTest() {

    lateinit var viewModel: NewsSourceViewModel

    override fun setup() {
        super.setup()
        viewModel = NewsSourceViewModel(newsUseCase)
    }

    @Test
    fun `fetch news source loading init`() {
        val category = "business"

        val displayStateObserver = mockk<Observer<DisplayStateArticle?>>(relaxed = true)

        viewModel.displayState.observeForever(displayStateObserver)

        val flowResult = flow { emit(DataState.Loading(null)) }
        every { newsUseCase.getSources(category) } returns flowResult

        // When
        viewModel.fetchNewsSource(category)

        // Then
        verify {
            displayStateObserver.onChanged(viewModel.displayState.value)
        }

        Assert.assertEquals(DisplayStateArticle(CHILD_INDEX_LOADING), viewModel.displayState.value)
    }

    @Test
    fun `fetch news soource error - from API`() {
        // Given
        val category = "business"
        val errorMessage = "something wrong"
        val mockDisplayState = DisplayStateArticle(CHILD_INDEX_WARNING, ERROR, errorMessage)

        val displayStateObserver = mockk<Observer<DisplayStateArticle?>>(relaxed = true)

        viewModel.displayState.observeForever(displayStateObserver)

        val flowResult = flow { emit(DataState.Failure<List<SourceUiState>>(errorMessage)) }
        every { newsUseCase.getSources(category) } returns flowResult

        // When
        viewModel.fetchNewsSource(category)

        // Then
        verifyOrder {
            displayStateObserver.onChanged(viewModel.displayState.value)
        }
        Assert.assertEquals(mockDisplayState, viewModel.displayState.value)
    }

    @Test
    fun `fetch news source - success data empty`() {
        // Given
        val category = "business"
        val mockDisplayState = DisplayStateArticle(CHILD_INDEX_WARNING, EMPTY_DATA)
        val mockSourceData = emptyList<SourceUiState>() /*listOf(
            SourceUiState(
                id = "",
                name = "",
                description = "",
                url = ""
            )
        )*/

        val displayStateObserver = mockk<Observer<DisplayStateArticle?>>(relaxed = true)
        val newsSourceObserver = mockk<Observer<List<SourceUiState>?>>(relaxed = true)

        viewModel.displayState.observeForever(displayStateObserver)
        viewModel.newsSourceLiveData.observeForever(newsSourceObserver)

        val flowResult = flow { emit(DataState.Success<List<SourceUiState>>(mockSourceData)) }
        every { newsUseCase.getSources(category) } returns flowResult

        // When
        viewModel.fetchNewsSource(category)

        // Then
        verifyOrder {
            displayStateObserver.onChanged(viewModel.displayState.value)
            newsSourceObserver.onChanged(viewModel.newsSourceLiveData.value)
        }

        Assert.assertEquals(mockDisplayState, viewModel.displayState.value)
        Assert.assertEquals(mockSourceData, viewModel.newsSourceLiveData.value)
    }

    @Test
    fun `fetch news source - success data from API`() {
        // Given
        val category = "business"
        val mockDisplayState = DisplayStateArticle(CHILD_INDEX_SUCCESS)
        val mockSourceData = listOf(
            SourceUiState(
                id = "",
                name = "",
                description = "",
                url = ""
            )
        )

        val displayStateObserver = mockk<Observer<DisplayStateArticle?>>(relaxed = true)
        val newsSourceObserver = mockk<Observer<List<SourceUiState>?>>(relaxed = true)

        viewModel.displayState.observeForever(displayStateObserver)
        viewModel.newsSourceLiveData.observeForever(newsSourceObserver)

        val flowResult = flow { emit(DataState.Success<List<SourceUiState>>(mockSourceData)) }
        every { newsUseCase.getSources(category) } returns flowResult

        // When
        viewModel.fetchNewsSource(category)

        // Then
        verifyOrder {
            displayStateObserver.onChanged(viewModel.displayState.value)
            newsSourceObserver.onChanged(viewModel.newsSourceLiveData.value)
        }

        Assert.assertEquals(mockDisplayState, viewModel.displayState.value)
        Assert.assertEquals(mockSourceData, viewModel.newsSourceLiveData.value)
    }
}