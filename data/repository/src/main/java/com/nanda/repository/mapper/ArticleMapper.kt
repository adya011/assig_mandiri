package com.nanda.repository.mapper

import com.nanda.remote.model.EverythingDto
import com.nanda.repository.helper.DataMapper
import com.nanda.repository.model.ArticleEntity

class ArticleMapper : DataMapper<EverythingDto, List<ArticleEntity>> {

    override fun mapDataModel(dataModel: EverythingDto?): List<ArticleEntity> =
        dataModel?.articles?.map {
            ArticleEntity(
                id = it.source?.id.orEmpty(),
                title = it.title.orEmpty(),
                description = it.description.orEmpty(),
                url = it.url.orEmpty(),
                urlToImage = it.urlToImage.orEmpty()
            )
        }.orEmpty()
}