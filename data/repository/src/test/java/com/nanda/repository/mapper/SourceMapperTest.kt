package com.nanda.repository.mapper

import com.nanda.remote.model.SourceDto
import com.nanda.remote.model.TopHeadlineDto
import org.junit.Assert
import org.junit.Test

class SourceMapperTest {
    private val mapper = SourceMapper()

    @Test
    fun `map source from dto to entity`() {
        val dtoData = TopHeadlineDto(
            status = "ok",
            sources = listOf(
                SourceDto(
                    id = "source-id",
                    name = "Source Name",
                    description = "Source description",
                    url = "source-url.com"
                )
            )
        )

        val result = mapper.mapDataModel(dtoData)

        Assert.assertEquals(dtoData.sources?.first()?.id, result.first().id)
        Assert.assertEquals(dtoData.sources?.first()?.name, result.first().name)
        Assert.assertEquals(dtoData.sources?.first()?.description, result.first().description)
        Assert.assertEquals(dtoData.sources?.first()?.url, result.first().url)
    }
}