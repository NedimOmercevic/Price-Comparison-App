package com.example.pricecomparisonapp.model.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pricecomparisonapp.model.data.local.entity.StoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {
    @Query("SELECT * FROM stores WHERE cityId = :cityId ORDER BY name")
    fun observeByCity(cityId: Long): Flow<List<StoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(store: StoreEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stores: List<StoreEntity>): List<Long>

    @Query("SELECT * FROM stores WHERE name = :name AND cityId = :cityId LIMIT 1")
    suspend fun getByNameAndCity(name: String, cityId: Long): StoreEntity?
}
