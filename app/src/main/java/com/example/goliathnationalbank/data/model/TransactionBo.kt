package com.example.goliathnationalbank.data.model

data class TransactionBo(
    val id: Long,
    val sku: String?,
    val amount: String?,
    val currency: String?
)