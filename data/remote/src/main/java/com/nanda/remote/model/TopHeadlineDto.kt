package com.nanda.remote.model

import com.squareup.moshi.Json

data class TopHeadlineDto(
    @Json(name = "status") val status: String,
    @Json(name = "sources") val sources: List<SourceDto>
)