package com.example.movieguideapp.domain.base

import com.example.movieguideapp.data.local.model.WorkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseUseCase<P,R>(private val dispatcher: CoroutineDispatcher = Dispatchers.Main) {

    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
       error.printStackTrace()
    }

    suspend operator fun invoke(param: P) = withContext(dispatcher + exceptionHandler) {
        execute(param)
    }

    abstract suspend fun execute(param: P): WorkResult<R>
}