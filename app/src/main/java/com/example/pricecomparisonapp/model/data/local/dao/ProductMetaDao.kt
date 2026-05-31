package com.example.pricecomparisonapp.model.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pricecomparisonapp.model.data.local.entity.ProductMetaEntity

@Dao
interface ProductMetaDao {
    @Query("SELECT * FROM product_meta")
    suspend fun getAll(): List<ProductMetaEntity>

    @Query("SELECT * FROM product_meta WHERE productId = :productId LIMIT 1")
    suspend fun getByProductId(productId: Long): ProductMetaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meta: ProductMetaEntity)

    @Query("DELETE FROM product_meta WHERE productId = :productId")
    suspend fun deleteByProductId(productId: Long)
}
