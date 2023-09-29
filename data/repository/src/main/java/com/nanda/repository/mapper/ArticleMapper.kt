package com.nanda.repository.mapper

import com.nanda.remote.model.EverythingDto
import com.nanda.repository.helper.DataMapper
import com.nanda.repository.model.ArticleEntity
import com.nanda.repository.model.ArticleItemEntity

class ArticleMapper : DataMapper<EverythingDto, ArticleEntity> {

    override fun mapDataModel(dataModel: EverythingDto?): ArticleEntity =
        ArticleEntity(
            total = dataModel?.totalResults ?: 0,
            articles = dataModel?.articles?.map {
                ArticleItemEntity(
                    id = it.source?.id.orEmpty(),
                    title = it.title.orEmpty(),
                    description = it.description.orEmpty(),
                    url = it.url.orEmpty(),
                    urlToImage = it.urlToImage.orEmpty()
                )
            }.orEmpty()
        )
}