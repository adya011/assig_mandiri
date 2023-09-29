package com.nanda.assig_mandiri.di

import com.nanda.assig_mandiri.feature.article.NewsArticleViewModel
import com.nanda.assig_mandiri.feature.source.NewsSourceViewModel
import com.nanda.domain.model.resource.AppDispatchers
import com.nanda.domain.usecase.NewsUseCase
import com.nanda.remote.api.NewsApi
import org.koin.dsl.module
import com.nanda.remote.util.RetrofitBuilder
import com.nanda.repository.NewsRepository
import com.nanda.repository.NewsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel

object Modules {
    private val networkModules = module {
        single {
            RetrofitBuilder.create(NewsApi::class.java)
        }
    }

    private val persistenceModule = module {
        factory { AppDispatchers(Dispatchers.Main, Dispatchers.IO, Dispatchers.Default) }
    }

    private val useCaseModules = module {
        single { NewsUseCase(get(), get()) }
    }

    private val viewModelModules = module {
        viewModel { NewsArticleViewModel(get()) }
        viewModel { NewsSourceViewModel(get()) }
    }

    private val repositoryModules = module {
        single<NewsRepository> { NewsRepositoryImpl(get()) }
    }

    fun getAppComponents() = listOf(
        networkModules,
        persistenceModule,
        useCaseModules,
        viewModelModules,
        repositoryModules
    )
}