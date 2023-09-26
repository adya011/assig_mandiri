package com.nanda.repository

import com.nanda.remote.api.NewsApi
import com.nanda.repository.helper.dataSourceHandling
import com.nanda.repository.mapper.ArticleMapper
import com.nanda.repository.mapper.SourceMapper
import com.nanda.repository.model.ArticleEntity
import com.nanda.repository.model.DataResult
import com.nanda.repository.model.SourceEntity

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository {

    override suspend fun getArticle(): DataResult<List<ArticleEntity>> {
        return dataSourceHandling(
            networkCall = { api.everything() },
            mapper = ArticleMapper()
        )
    }

    override suspend fun getSource(category: String): DataResult<List<SourceEntity>> {
        return dataSourceHandling(
            networkCall = { api.topHeadlines(category) },
            mapper = SourceMapper()
        )
    }

}