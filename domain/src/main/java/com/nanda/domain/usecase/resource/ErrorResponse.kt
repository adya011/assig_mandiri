package com.nanda.domain.model.resource

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "error") val error: Boolean? = null,
    @Json(name = "status") val status: Int? = null,
    @Json(name = "message") val message: String? = null,
    @Json(name = "description") val description: String? = null
)