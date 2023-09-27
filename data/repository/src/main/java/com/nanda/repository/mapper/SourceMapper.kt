package com.nanda.repository.mapper

import com.nanda.remote.model.TopHeadlineDto
import com.nanda.repository.helper.DataMapper
import com.nanda.repository.model.SourceEntity

class SourceMapper : DataMapper<TopHeadlineDto, List<SourceEntity>> {

    override fun mapDataModel(dataModel: TopHeadlineDto?): List<SourceEntity> =
        dataModel?.sources?.map {
            SourceEntity(
                id = it.id,
                name = it.name,
                description = it.description,
                url = it.url
            )
        }.orEmpty()
}