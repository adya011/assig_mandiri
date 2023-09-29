package com.nanda.repository.mapper

import com.nanda.remote.model.ArticleDto
import com.nanda.remote.model.ArticleSourceDto
import com.nanda.remote.model.EverythingDto
import org.junit.Assert
import org.junit.Test

class ArticleMapperTest {
    private val mapper = ArticleMapper()

    @Test
    fun `map article from dto to entity`() {
        val dtoData = EverythingDto(
            status = "ok",
            totalResults = 16,
            articles = listOf(
                ArticleDto(
                    source = ArticleSourceDto(
                        id = "source",
                        name = "Source"
                    ),
                    title = "Artice Title",
                    description = "Article description",
                    url = "article-url.com",
                    urlToImage = "article-icon.jpg"
                )
            )
        )

        val result = mapper.mapDataModel(dtoData)

        Assert.assertEquals(dtoData.totalResults, result.total)
        Assert.assertEquals(
            dtoData.articles?.first()?.source?.id,
            result.articles.first().id
        )
        Assert.assertEquals(
            dtoData.articles?.first()?.title,
            result.articles.first().title
        )
        Assert.assertEquals(
            dtoData.articles?.first()?.description,
            result.articles.first().description
        )
        Assert.assertEquals(
            dtoData.articles?.first()?.url,
            result.articles.first().url
        )
        Assert.assertEquals(
            dtoData.articles?.first()?.urlToImage,
            result.articles.first().urlToImage
        )
    }
}