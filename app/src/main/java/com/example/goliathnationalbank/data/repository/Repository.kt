package com.example.goliathnationalbank.data.repository

import androidx.paging.PagingSource
import com.example.goliathnationalbank.data.datasource.LocalDataSource
import com.example.goliathnationalbank.data.datasource.RemoteDataSource
import com.example.goliathnationalbank.data.model.RateBo
import com.example.goliathnationalbank.data.model.TransactionBo
import com.example.goliathnationalbank.data.response.CacheableRemoteResponse
import com.example.goliathnationalbank.data.response.LocalResponse
import com.example.goliathnationalbank.data.response.RepositoryResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

interface Repository {

    suspend fun getRates(): RepositoryResponse<List<RateBo>?>

    suspend fun getAllTransactions(): RepositoryResponse<List<TransactionBo>?>

    fun getProducts(): PagingSource<Int, String>

    fun getTransactionsBySku(sku: String): RepositoryResponse<List<TransactionBo>>
}

@ActivityRetainedScoped
class RepositoryImpl @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) : Repository {

    override suspend fun getRates(): RepositoryResponse<List<RateBo>?> {
        return object : CacheableRemoteResponse<List<RateBo>?>() {
            override suspend fun loadFromLocal(): List<RateBo>? {
                return local.getRates()
            }

            override fun shouldRequestFromRemote(localResponse: List<RateBo>?): Boolean {
                return localResponse.isNullOrEmpty()
            }

            override suspend fun requestRemoteCall(): List<RateBo>? {
                return remote.getRates()
            }

            override suspend fun saveRemoteResponse(remoteResponse: List<RateBo>?) {
                remoteResponse?.let { local.saveRates(it) }
            }
        }.build()
    }

    override suspend fun getAllTransactions(): RepositoryResponse<List<TransactionBo>?> {
        return object : CacheableRemoteResponse<List<TransactionBo>?>() {
            override suspend fun loadFromLocal(): List<TransactionBo>? {
                return local.getAllTransactions()
            }

            override fun shouldRequestFromRemote(localResponse: List<TransactionBo>?): Boolean {
                return localResponse.isNullOrEmpty()
            }

            override suspend fun requestRemoteCall(): List<TransactionBo>? {
                return remote.getAllTransactions()
            }

            override suspend fun saveRemoteResponse(remoteResponse: List<TransactionBo>?) {
                remoteResponse?.let { local.saveTransactions(it) }
            }
        }.build()
    }

    override fun getProducts(): PagingSource<Int, String> {
        return local.getProducts()
    }

    override fun getTransactionsBySku(sku: String): RepositoryResponse<List<TransactionBo>> {
        return object : LocalResponse<List<TransactionBo>>() {
            override suspend fun loadFromLocal(): List<TransactionBo>? {
                return local.getTransactionsBySku(sku)
            }

        }.build()
    }

}