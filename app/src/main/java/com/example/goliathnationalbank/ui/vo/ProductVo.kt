package com.example.goliathnationalbank.ui.vo

import com.example.goliathnationalbank.data.model.TransactionBo

data class ProductVo(
    var sku: String,
    var numberTransactions: Int,
    var totalAmount: Double,
    var transactions: List<TransactionBo>? = null
)