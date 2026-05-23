package com.example.pricecomparisonapp.model.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pricecomparisonapp.model.data.local.entity.PriceOfferEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PriceOfferDao {
    @Query("SELECT * FROM price_offers WHERE productId = :productId")
    fun observeByProduct(productId: Long): Flow<List<PriceOfferEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(offer: PriceOfferEntity): Long

    @Update
    suspend fun update(offer: PriceOfferEntity)

    @Delete
    suspend fun delete(offer: PriceOfferEntity)

    @Query("SELECT MIN(priceBam) FROM price_offers WHERE productId = :productId")
    suspend fun getLowestPrice(productId: Long): Double?
}
