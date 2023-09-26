package com.nanda.remote.model

import com.squareup.moshi.Json

data class EverythingDto(
    @Json(name = "status") val status: String,
    @Json(name = "articles") val articles: List<ArticleDto>
)