package com.example.goliathnationalbank.data.local.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "rateEntity",
    primaryKeys = ["from", "to"]
)
data class RateDbo(
    val from: String,
    @ColumnInfo(name = "to")
    val toParam: String,
    val rate: String? = null
)