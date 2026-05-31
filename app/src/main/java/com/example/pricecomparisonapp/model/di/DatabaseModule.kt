package com.example.pricecomparisonapp.model.di

import android.content.Context
import androidx.room.Room
import com.example.pricecomparisonapp.model.data.local.dao.CatalogDao
import com.example.pricecomparisonapp.model.data.local.dao.CategoryDao
import com.example.pricecomparisonapp.model.data.local.dao.CityDao
import com.example.pricecomparisonapp.model.data.local.dao.FavoriteDao
import com.example.pricecomparisonapp.model.data.local.dao.PriceOfferDao
import com.example.pricecomparisonapp.model.data.local.dao.ProductDao
import com.example.pricecomparisonapp.model.data.local.dao.ProductMetaDao
import com.example.pricecomparisonapp.model.data.local.dao.StoreDao
import com.example.pricecomparisonapp.model.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "price_comparison.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides fun provideCityDao(db: AppDatabase): CityDao = db.cityDao()
    @Provides fun provideStoreDao(db: AppDatabase): StoreDao = db.storeDao()
    @Provides fun provideCategoryDao(db: AppDatabase): CategoryDao = db.categoryDao()
    @Provides fun provideProductDao(db: AppDatabase): ProductDao = db.productDao()
    @Provides fun providePriceOfferDao(db: AppDatabase): PriceOfferDao = db.priceOfferDao()
    @Provides fun provideFavoriteDao(db: AppDatabase): FavoriteDao = db.favoriteDao()
    @Provides fun provideCatalogDao(db: AppDatabase): CatalogDao = db.catalogDao()
    @Provides fun provideProductMetaDao(db: AppDatabase): ProductMetaDao = db.productMetaDao()
}
