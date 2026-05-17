package com.example.pricecomparisonapp.model.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.pricecomparisonapp.model.data.local.entity.CityEntity
import com.example.pricecomparisonapp.model.data.local.entity.CityWithStores
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Query("SELECT * FROM cities ORDER BY name")
    fun observeCities(): Flow<List<CityEntity>>

    @Transaction
    @Query("SELECT * FROM cities ORDER BY name")
    fun observeCitiesWithStores(): Flow<List<CityWithStores>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cities: List<CityEntity>): List<Long>

    @Query("SELECT * FROM cities WHERE id = :cityId LIMIT 1")
    suspend fun getById(cityId: Long): CityEntity?

    @Query("SELECT COUNT(*) FROM cities")
    suspend fun count(): Int

    @Query("SELECT * FROM cities WHERE name = :name LIMIT 1")
    suspend fun getByName(name: String): CityEntity?
}
