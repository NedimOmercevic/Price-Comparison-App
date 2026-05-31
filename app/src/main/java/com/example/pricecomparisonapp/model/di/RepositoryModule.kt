package com.example.pricecomparisonapp.model.di

import com.example.pricecomparisonapp.model.repository.CategoryRepository
import com.example.pricecomparisonapp.model.repository.CategoryRepositoryImpl
import com.example.pricecomparisonapp.model.repository.CityRepository
import com.example.pricecomparisonapp.model.repository.CityRepositoryImpl
import com.example.pricecomparisonapp.model.repository.ProductNetworkRepository
import com.example.pricecomparisonapp.model.repository.ProductNetworkRepositoryImpl
import com.example.pricecomparisonapp.model.repository.ProductRepository
import com.example.pricecomparisonapp.model.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindProductNetworkRepository(impl: ProductNetworkRepositoryImpl): ProductNetworkRepository

    @Binds
    @Singleton
    abstract fun bindCityRepository(impl: CityRepositoryImpl): CityRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository
}
