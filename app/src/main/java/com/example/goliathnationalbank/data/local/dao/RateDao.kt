package com.example.goliathnationalbank.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.goliathnationalbank.data.local.dbo.RateDbo

@Dao
interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRates(list: List<RateDbo>)

    @Query("SELECT * FROM rateEntity")
    suspend fun getRates(): List<RateDbo>

}