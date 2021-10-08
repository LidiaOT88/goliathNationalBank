package com.example.goliathnationalbank.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.goliathnationalbank.data.local.dao.RateDao
import com.example.goliathnationalbank.data.local.dao.TransactionDao
import com.example.goliathnationalbank.data.local.dbo.RateDbo
import com.example.goliathnationalbank.data.local.dbo.TransactionDbo

@Database(
    entities = [
        RateDbo::class,
        TransactionDbo::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getRateDao(): RateDao
    abstract fun getTransactionDao(): TransactionDao

}