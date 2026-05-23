package com.example.pricecomparisonapp.model.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.pricecomparisonapp.model.data.local.entity.ProductEntity
import com.example.pricecomparisonapp.model.data.local.entity.ProductWithOffers
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products ORDER BY name")
    fun observeAll(): Flow<List<ProductEntity>>

    @Transaction
    @Query("SELECT * FROM products ORDER BY name")
    fun observeAllWithOffers(): Flow<List<ProductWithOffers>>

    @Query("SELECT * FROM products WHERE id = :productId LIMIT 1")
    suspend fun getById(productId: Long): ProductEntity?

    @Transaction
    @Query("SELECT * FROM products WHERE id = :productId LIMIT 1")
    suspend fun getWithOffers(productId: Long): ProductWithOffers?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductEntity): Long

    @Update
    suspend fun update(product: ProductEntity)

    @Delete
    suspend fun delete(product: ProductEntity)
}
