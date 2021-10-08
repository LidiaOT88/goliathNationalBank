package com.example.goliathnationalbank.data.datasource

import androidx.paging.PagingSource
import com.example.goliathnationalbank.data.model.RateBo
import com.example.goliathnationalbank.data.model.TransactionBo

interface LocalDataSource {

    suspend fun saveRates(rates: List<RateBo>)

    suspend fun getRates(): List<RateBo>?

    suspend fun saveTransactions(transactions: List<TransactionBo>)

    suspend fun getAllTransactions(): List<TransactionBo>?

    suspend fun getTransactionsBySku(productName: String): List<TransactionBo>?

    fun getProducts(): PagingSource<Int, String>

    fun getNumberTransactionsBySku(sku: String): Int
}
