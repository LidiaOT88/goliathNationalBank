package com.example.goliathnationalbank.other

import com.example.goliathnationalbank.data.local.dbo.RateDbo
import com.example.goliathnationalbank.data.local.dbo.TransactionDbo
import com.example.goliathnationalbank.data.model.RateBo
import com.example.goliathnationalbank.data.model.TransactionBo
import com.example.goliathnationalbank.data.remote.dto.RateDto
import com.example.goliathnationalbank.data.remote.dto.TransactionDto
import com.example.goliathnationalbank.other.Constants.RateConstants.AUD
import com.example.goliathnationalbank.other.Constants.RateConstants.CAD
import com.example.goliathnationalbank.other.Constants.RateConstants.EUR
import com.example.goliathnationalbank.other.Constants.RateConstants.USD
import com.example.goliathnationalbank.ui.vo.ProductVo
import java.math.BigDecimal
import java.math.RoundingMode

object Utils {

    fun RateDto.toBo() = RateBo(
        this.from,
        this.toParam,
        this.rate
    )

    fun TransactionDto.toBo(id: Long) = TransactionBo(
        id,
        this.sku,
        this.amount,
        this.currency
    )

    fun RateDbo.toBo() = RateBo(
        this.from,
        this.toParam,
        this.rate
    )

    fun RateBo.toDbo() = RateDbo(
        this.from ?: "",
        this.toParam ?: "",
        this.rate
    )

    fun TransactionDbo.toBo() = TransactionBo(
        this.id,
        this.sku,
        this.amount,
        this.currency
    )

    fun TransactionBo.toDbo() = TransactionDbo(
        this.id,
        this.sku ?: "",
        this.amount,
        this.currency
    )

    fun convertToProductVo(sku: String, transactions: List<TransactionBo>): ProductVo {
        val totalTransactions = transactions.size
        val totalAmount = getTotalAmountBySku(transactions)
        return ProductVo(sku, totalTransactions, totalAmount, transactions)
    }

    fun getTotalAmountBySku(transactions: List<TransactionBo>?): Double {
        var amount = 0.0
        transactions?.forEach {
            amount += changeRate(it.currency ?: EUR, it.amount ?: "0.0")
        }
        return amount
    }

    private fun changeRate(currency: String, amount: String): Double {
        //TODO: función recursiva para cambio de moneda
        val amountDouble = amount.toDouble()
        return BigDecimal(
            when (currency) {
                CAD -> {
                    amountDouble * 0.66
                }
                USD -> {
                    amountDouble * 0.66 * 1.02
                }
                AUD -> {
                    amountDouble * 1.02 * 0.66 * 1.02
                }
                else -> amountDouble
            }

        ).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }
}