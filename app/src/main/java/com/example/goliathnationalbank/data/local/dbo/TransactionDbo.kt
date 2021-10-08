package com.example.goliathnationalbank.data.local.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactionEntity"
)
class TransactionDbo(
    @PrimaryKey
    val id: Long,
    var sku: String,
    var amount: String? = null,
    var currency: String? = null
)