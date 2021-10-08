package com.example.goliathnationalbank.data.datasource

import com.example.goliathnationalbank.data.model.RateBo
import com.example.goliathnationalbank.data.model.TransactionBo

interface RemoteDataSource {

    suspend fun getRates(): List<RateBo>?

    suspend fun getAllTransactions(): List<TransactionBo>?

}