package com.example.goliathnationalbank.data.usecase

import com.example.goliathnationalbank.data.lifecycle.AsyncResult
import com.example.goliathnationalbank.data.lifecycle.Event
import com.example.goliathnationalbank.data.repository.Repository
import com.example.goliathnationalbank.other.Constants.ErrorMessage.ERROR_GETTING_DATA
import com.example.goliathnationalbank.other.Constants.ErrorMessage.ERROR_NO_DATA
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetInitialDataUseCase {

    suspend operator fun invoke(): Flow<Event<AsyncResult<Boolean>>>

}

class GetInitialDataUseCaseImpl @Inject constructor(
    private val repository: Repository,
) : GetInitialDataUseCase {

    override suspend operator fun invoke(): Flow<Event<AsyncResult<Boolean>>> {
        return flow {
            emit(AsyncResult.Loading(null))

            val getRatesResponse = repository.getRates().valueAsync().await()

            if (getRatesResponse is AsyncResult.Success) {
                if (getRatesResponse.data.isNullOrEmpty()) {
                    emit(
                        AsyncResult.Error(
                            ERROR_NO_DATA, null
                        )
                    )

                } else {
                    getTransactionsBlock()
                }

            } else {
                emit(
                    AsyncResult.Error(ERROR_GETTING_DATA, false)
                )
            }
        }.map { Event(it) }
    }

    private suspend fun FlowCollector<AsyncResult<Boolean>>.getTransactionsBlock() {
        val getTransactions = repository.getAllTransactions().valueAsync().await()

        if (getTransactions is AsyncResult.Success) {
            if (getTransactions.data.isNullOrEmpty()) {
                emit(
                    AsyncResult.Error(
                        ERROR_NO_DATA, null
                    )
                )

            } else {
                emit(AsyncResult.Success(true))
            }

        } else {
            emit(
                AsyncResult.Error(ERROR_GETTING_DATA, false)
            )
        }
    }

}