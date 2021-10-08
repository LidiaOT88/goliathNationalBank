package com.example.goliathnationalbank.data.remote

import com.example.goliathnationalbank.data.remote.dto.RateDto
import com.example.goliathnationalbank.data.remote.dto.TransactionDto
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    companion object {
        const val HEADER = "Accept: application/json"
        const val RATES_URL = "rates.json"
        const val TRANSACTIONS_URL = "transactions.json"
    }

    @Headers(HEADER)
    @GET(RATES_URL)
    suspend fun getRates(): List<RateDto>?

    @Headers(HEADER)
    @GET(TRANSACTIONS_URL)
    suspend fun getAllTransactions(): List<TransactionDto>?

}