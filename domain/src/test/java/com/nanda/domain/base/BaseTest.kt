package com.nanda.domain.base

import com.nanda.domain.model.resource.AppDispatchers
import com.nanda.repository.NewsRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
open class BaseTest {
    protected lateinit var appDispatchers: AppDispatchers
    protected val newsRepository: NewsRepository = mockk(relaxed = true)

    @Before
    open fun setup() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        appDispatchers = AppDispatchers(testDispatcher, testDispatcher, testDispatcher)
    }

    @After
    open fun cleanUp() {
        Dispatchers.resetMain()
    }
}