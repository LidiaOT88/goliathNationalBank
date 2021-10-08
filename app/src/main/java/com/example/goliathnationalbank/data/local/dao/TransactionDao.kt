package com.example.goliathnationalbank.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.goliathnationalbank.data.local.dbo.TransactionDbo

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransactions(list: List<TransactionDbo>)

    @Query("SELECT * FROM transactionEntity")
    suspend fun getTransactions(): List<TransactionDbo>

    @Query("SELECT DISTINCT sku FROM transactionEntity")
    fun getAllProducts(): PagingSource<Int, String>

    @Query("SELECT * FROM transactionEntity WHERE sku = :sku")
    suspend fun getTransactionsBySku(sku: String): List<TransactionDbo>?

    @Query("SELECT COUNT(*) FROM transactionEntity WHERE sku = :sku")
    fun getNumberTransactionsBySku(sku: String): Int

/*    @Query("SELECT * FROM transactionEntity")
    fun getAllTransactionsSku(): PagingSource<Int, TransactionDbo>*/

/*    @Query("SELECT DISTINCT sku FROM transactionEntity")
    fun getAllProducts(): List<String>?*/

}