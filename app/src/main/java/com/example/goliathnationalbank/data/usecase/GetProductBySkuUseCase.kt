package com.example.goliathnationalbank.data.usecase

import com.example.goliathnationalbank.data.lifecycle.AsyncResult
import com.example.goliathnationalbank.data.lifecycle.Event
import com.example.goliathnationalbank.data.repository.Repository
import com.example.goliathnationalbank.other.Constants
import com.example.goliathnationalbank.other.Constants.ErrorMessage.ERROR_GETTING_DATA
import com.example.goliathnationalbank.other.Utils
import com.example.goliathnationalbank.ui.vo.ProductVo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetProductBySkuUseCase {

    suspend operator fun invoke(sku: String): Flow<Event<AsyncResult<ProductVo?>>>

}

class GetProductBySkuUseCaseImpl @Inject constructor(
    private val repository: Repository,
) : GetProductBySkuUseCase {

    override suspend operator fun invoke(sku: String): Flow<Event<AsyncResult<ProductVo?>>> {
        return flow {
            emit(AsyncResult.Loading(null))

            val transactionsBySkuResponse = repository.getTransactionsBySku(sku).valueAsync().await()

            if (transactionsBySkuResponse is AsyncResult.Success) {
                val productVo = transactionsBySkuResponse.data?.let {
                    Utils.convertToProductVo(sku, it)
                }
                emit(
                    AsyncResult.Success(
                        productVo
                    )
                )


            } else {
                emit(
                    AsyncResult.Error(
                        ERROR_GETTING_DATA, null
                    )
                )
            }

        }.map { Event(it) }
    }

}