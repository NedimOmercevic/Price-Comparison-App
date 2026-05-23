package com.example.pricecomparisonapp.model.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pricecomparisonapp.model.data.local.dao.CatalogDao
import com.example.pricecomparisonapp.model.data.local.dao.CategoryDao
import com.example.pricecomparisonapp.model.data.local.dao.CityDao
import com.example.pricecomparisonapp.model.data.local.dao.FavoriteDao
import com.example.pricecomparisonapp.model.data.local.dao.PriceOfferDao
import com.example.pricecomparisonapp.model.data.local.dao.ProductDao
import com.example.pricecomparisonapp.model.data.local.dao.StoreDao
import com.example.pricecomparisonapp.model.data.local.entity.CategoryEntity
import com.example.pricecomparisonapp.model.data.local.entity.CityEntity
import com.example.pricecomparisonapp.model.data.local.entity.FavoriteEntity
import com.example.pricecomparisonapp.model.data.local.entity.PriceOfferEntity
import com.example.pricecomparisonapp.model.data.local.entity.ProductEntity
import com.example.pricecomparisonapp.model.data.local.entity.StoreEntity

@Database(
    entities = [
        CityEntity::class,
        StoreEntity::class,
        CategoryEntity::class,
        ProductEntity::class,
        PriceOfferEntity::class,
        FavoriteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun storeDao(): StoreDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
    abstract fun priceOfferDao(): PriceOfferDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun catalogDao(): CatalogDao
}
