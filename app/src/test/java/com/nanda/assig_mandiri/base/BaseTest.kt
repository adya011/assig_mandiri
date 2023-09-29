package com.nanda.assig_mandiri.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nanda.domain.usecase.NewsUseCase
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@Suppress("UnnecessaryAbstractClass")
abstract class BaseTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    val testDispatchers = UnconfinedTestDispatcher()

    lateinit var newsUseCase: NewsUseCase

    @Before
    open fun setup() {
        Dispatchers.setMain(testDispatchers)
        newsUseCase = mockk()
    }

    @After
    open fun cleanup() {
        Dispatchers.resetMain()
    }
}