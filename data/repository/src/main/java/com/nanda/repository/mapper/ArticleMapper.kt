package com.nanda.repository.mapper

import com.nanda.remote.model.EverythingDto
import com.nanda.repository.helper.DataMapper
import com.nanda.repository.model.ArticleEntity

class ArticleMapper : DataMapper<EverythingDto, List<ArticleEntity>> {

    override fun mapDataModel(dataModel: EverythingDto?): List<ArticleEntity> =
        dataModel?.articles?.map {
            ArticleEntity(
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content
            )
        }.orEmpty()
}