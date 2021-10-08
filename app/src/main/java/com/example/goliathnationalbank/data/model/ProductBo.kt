package com.example.goliathnationalbank.data.model

data class ProductBo(
    val sku: String,
    val numberTransactions: Int,
    val transactions: List<TransactionBo>
)