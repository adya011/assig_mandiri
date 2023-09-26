package com.nanda.repository.model


sealed class DataResult<T> {
    data class Success<T>(val body: T, val dataType: DataType? = null) : DataResult<T>()
    data class Error<T>(val message: String, val code: Int) : DataResult<T>()
}