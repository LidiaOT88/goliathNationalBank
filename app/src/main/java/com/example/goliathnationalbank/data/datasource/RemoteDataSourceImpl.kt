package com.example.goliathnationalbank.data.datasource

import com.example.goliathnationalbank.data.model.TransactionBo
import com.example.goliathnationalbank.data.remote.ApiService
import com.example.goliathnationalbank.data.remote.RemoteErrorManagement
import com.example.goliathnationalbank.other.Utils.toBo
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun getRates() = RemoteErrorManagement.wrap {
        apiService.getRates() ?: listOf()
    }.map { it.toBo() }

    override suspend fun getAllTransactions(): List<TransactionBo> =
        RemoteErrorManagement.wrap {
            apiService.getAllTransactions() ?: listOf()
        }.mapIndexed { index, transactionBo ->
            transactionBo.toBo((index + 1).toLong())
        }

}