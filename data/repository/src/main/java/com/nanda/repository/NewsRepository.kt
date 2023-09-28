package com.nanda.repository

import com.nanda.repository.model.ArticleEntity
import com.nanda.repository.model.DataResult
import com.nanda.repository.model.SourceEntity

interface NewsRepository {
    suspend fun getArticle(source: String, query: String): DataResult<List<ArticleEntity>>
    suspend fun getSource(category: String): DataResult<List<SourceEntity>>
}