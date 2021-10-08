package com.example.goliathnationalbank.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TransactionDto {

    @SerializedName("sku")
    @Expose
    var sku: String? = null

    @SerializedName("amount")
    @Expose
    var amount: String? = null

    @SerializedName("currency")
    @Expose
    var currency: String? = null

}