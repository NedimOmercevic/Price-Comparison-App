package com.example.pricecomparisonapp.model.repository

import com.example.pricecomparisonapp.model.data.ProductItem
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun observeProducts(): Flow<List<ProductItem>>
    suspend fun getProduct(productId: Long): ProductItem?
    suspend fun addProduct(
        name: String,
        categoryName: String,
        storeName: String,
        cityName: String,
        priceBam: Double
    ): Long
    suspend fun updateProductName(productId: Long, name: String)
    suspend fun deleteProduct(productId: Long)
    suspend fun toggleFavorite(productId: Long)
    fun observeFavorites(): Flow<List<ProductItem>>
}
