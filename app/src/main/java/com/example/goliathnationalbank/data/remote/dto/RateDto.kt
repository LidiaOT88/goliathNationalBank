package com.example.goliathnationalbank.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RateDto {

    @SerializedName("from")
    @Expose
    val from: String? = null

    @SerializedName("to")
    @Expose
    val toParam: String? = null

    @SerializedName("rate")
    @Expose
    val rate: String? = null

}