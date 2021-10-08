package com.example.goliathnationalbank.data.datasource

import androidx.paging.PagingSource
import com.example.goliathnationalbank.data.local.dao.RateDao
import com.example.goliathnationalbank.data.local.dao.TransactionDao
import com.example.goliathnationalbank.data.model.RateBo
import com.example.goliathnationalbank.data.model.TransactionBo
import com.example.goliathnationalbank.data.remote.RemoteErrorManagement
import com.example.goliathnationalbank.other.Utils.toBo
import com.example.goliathnationalbank.other.Utils.toDbo
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val rateDao: RateDao,
    private val transactionDao: TransactionDao,
) : LocalDataSource {

    override suspend fun saveRates(rates: List<RateBo>) {
        rateDao.saveRates(rates.map { it.toDbo() })
    }

    override suspend fun getRates() = RemoteErrorManagement.wrap {
        rateDao.getRates().map { it.toBo() }
    }

    override suspend fun saveTransactions(transactions: List<TransactionBo>) {
        transactionDao.saveTransactions(transactions.map { it.toDbo() })
    }

    override suspend fun getAllTransactions() = RemoteErrorManagement.wrap {
        transactionDao.getTransactions().map { it.toBo() }
    }

    override suspend fun getTransactionsBySku(sku: String): List<TransactionBo>? {
        return transactionDao.getTransactionsBySku(sku)?.map { it.toBo() }
    }

    override fun getProducts(): PagingSource<Int, String> {
        return transactionDao.getAllProducts()
    }

    override fun getNumberTransactionsBySku(sku: String): Int {
        return transactionDao.getNumberTransactionsBySku(sku)
    }

}