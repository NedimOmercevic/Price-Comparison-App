package com.example.pricecomparisonapp.model.repository

import com.example.pricecomparisonapp.model.data.ProductItem
import com.example.pricecomparisonapp.model.data.remote.dto.ProductDto
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun observeProducts(): Flow<List<ProductItem>>
    suspend fun syncFromNetwork(products: List<ProductDto>)
    suspend fun refreshProducts(): Result<Unit>
    suspend fun getProduct(productId: Long): ProductItem?
    suspend fun saveProductMeta(productId: Long, storeName: String, cityName: String)
    suspend fun removeProductFromCache(productId: Long)
    suspend fun mapDtoToProductItem(dto: ProductDto): ProductItem
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
