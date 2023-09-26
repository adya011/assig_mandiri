package com.nanda.domain.model.resource

import java.lang.Exception

data class DomainException(
    var errorType: DomainError,
    override val message: String?
): Exception()