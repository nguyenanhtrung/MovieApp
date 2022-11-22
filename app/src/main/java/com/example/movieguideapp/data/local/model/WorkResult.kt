package com.example.movieguideapp.data.local.model

sealed class WorkResult<T> {
    data class Success<T>(val data: T): WorkResult<T>()
    data class Error<T>(val code: Int, val message: String): WorkResult<T>()
    data class Failure<T>(val throwable: Throwable): WorkResult<T>()
}
