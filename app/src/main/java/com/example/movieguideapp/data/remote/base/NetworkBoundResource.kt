package com.example.movieguideapp.data.remote.base

import com.example.movieguideapp.data.local.model.WorkResult
import retrofit2.HttpException
import java.net.SocketTimeoutException

abstract class NetworkBoundResource<P,M> {

    suspend fun execute(param: P): WorkResult<M> {
        return try {
            if (shouldFetchFromNetwork(param)) {
                val result = fetchFromNetwork(param)
                return WorkResult.Success(result)
            }
            val localData = fetchFromLocal(param)
            WorkResult.Success(localData)
        } catch (timeoutException: SocketTimeoutException) {
            WorkResult.Error(code = 9, message = "")
        } catch (httpException: HttpException) {
            WorkResult.Error(code = httpException.code(), message = httpException.message ?: "")
        } catch (throwable: Throwable) {
            WorkResult.Failure(throwable)
        }
    }


    abstract suspend fun fetchFromNetwork(param: P): M
    abstract suspend fun fetchFromLocal(param: P): M
    open fun shouldFetchFromNetwork(param: P): Boolean = true
}