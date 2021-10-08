package com.example.goliathnationalbank.data.response

import com.example.goliathnationalbank.data.lifecycle.AsyncResult
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class RemoteResponse<ResultType> {

    private var flow: Flow<AsyncResult<ResultType>>? = null
    private var deferredValue: Deferred<AsyncResult<ResultType>>? = null

    //region RepositoryResponse
    private val response = object : RepositoryResponse<ResultType> {
        override suspend fun flow() = executeFlow()
        override suspend fun valueAsync() = executeAsync()
    }
    //endregion

    //region Logic Template
    fun build(): RepositoryResponse<ResultType> {
        return response
    }

    private suspend fun executeFlow(): Flow<AsyncResult<ResultType>> {
        return flow ?: flow {
            executeBase { emit(it) }
        }.apply { flow = this }
    }

    private suspend fun executeAsync(): Deferred<AsyncResult<ResultType>> {
        return deferredValue ?: coroutineScope {
            async {
                executeBase { }
            }
        }.apply { deferredValue = this }
    }

    private suspend fun executeBase(emit: suspend (AsyncResult<ResultType>) -> Unit): AsyncResult<ResultType> {
        emit(AsyncResult.Loading(null))
        val finalValue: AsyncResult<ResultType> = try {
                val networkResponse = fetchFromNetwork()
                AsyncResult.Success(networkResponse)
            } catch (e: Exception) {
                AsyncResult.Error(e.message.toString(), null)
            }
        emit(finalValue)
        return finalValue
    }

    private suspend fun fetchFromNetwork(): ResultType {
        return requestRemoteCall()
    }
    //endregion

    //region Abstract methods
    protected abstract suspend fun requestRemoteCall(): ResultType
    //endregion
}
