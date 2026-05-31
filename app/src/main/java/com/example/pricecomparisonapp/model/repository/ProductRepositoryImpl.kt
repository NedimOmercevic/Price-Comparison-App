package com.example.pricecomparisonapp.model.repository

import com.example.pricecomparisonapp.model.data.ProductItem
import com.example.pricecomparisonapp.model.data.local.dao.FavoriteDao
import com.example.pricecomparisonapp.model.data.local.dao.ProductMetaDao
import com.example.pricecomparisonapp.model.data.local.entity.FavoriteEntity
import com.example.pricecomparisonapp.model.data.local.entity.ProductMetaEntity
import com.example.pricecomparisonapp.model.data.remote.dto.ProductDto
import com.example.pricecomparisonapp.model.repository.mappers.buildCreateProductRequest
import com.example.pricecomparisonapp.model.repository.mappers.buildUpdateProductRequest
import com.example.pricecomparisonapp.model.repository.mappers.toProductItem
import com.example.pricecomparisonapp.model.repository.mappers.toProductItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val productNetworkRepository: ProductNetworkRepository,
    private val favoriteDao: FavoriteDao,
    private val productMetaDao: ProductMetaDao
) : ProductRepository {

    private val productsState = MutableStateFlow<List<ProductItem>>(emptyList())

    override fun observeProducts(): Flow<List<ProductItem>> = productsState.asStateFlow()

    override suspend fun syncFromNetwork(products: List<ProductDto>) {
        updateCache(products)
    }

    override suspend fun refreshProducts(): Result<Unit> {
        return productNetworkRepository.getProducts().mapCatching { updateCache(it) }
    }

    override suspend fun getProduct(productId: Long): ProductItem? {
        val cached = productsState.value.firstOrNull { it.id == productId }
        if (cached != null) return cached

        return productNetworkRepository.getProduct(productId).getOrNull()?.let { mapDtoToProductItem(it) }
    }

    override suspend fun saveProductMeta(productId: Long, storeName: String, cityName: String) {
        productMetaDao.upsert(
            ProductMetaEntity(
                productId = productId,
                storeName = storeName.trim(),
                cityName = cityName.trim()
            )
        )
    }

    override suspend fun removeProductFromCache(productId: Long) {
        productMetaDao.deleteByProductId(productId)
        favoriteDao.deleteByProductId(productId)
        productsState.value = productsState.value.filter { it.id != productId }
    }

    override suspend fun mapDtoToProductItem(dto: ProductDto): ProductItem {
        val favoriteIds = favoriteDao.getFavoriteIds().toSet()
        val meta = productMetaDao.getByProductId(dto.id.toLong())
        return dto.toProductItem(
            isFavorite = favoriteIds.contains(dto.id.toLong()),
            meta = meta
        )
    }

    override suspend fun addProduct(
        name: String,
        categoryName: String,
        storeName: String,
        cityName: String,
        priceBam: Double
    ): Long {
        val request = buildCreateProductRequest(name, categoryName, storeName, cityName, priceBam)
        val dto = productNetworkRepository.createProduct(request).getOrThrow()
        saveProductMeta(dto.id.toLong(), storeName, cityName)
        refreshProducts().getOrThrow()
        return dto.id.toLong()
    }

    override suspend fun updateProductName(productId: Long, name: String) {
        val current = getProduct(productId) ?: return
        val request = buildUpdateProductRequest(
            name = name,
            categoryName = current.categoryName,
            storeName = current.storeName,
            cityName = current.cityName,
            priceBam = current.priceBam
        )
        productNetworkRepository.updateProduct(productId, request).getOrThrow()
        refreshProducts().getOrThrow()
    }

    override suspend fun deleteProduct(productId: Long) {
        productNetworkRepository.deleteProduct(productId).getOrThrow()
        removeProductFromCache(productId)
    }

    override suspend fun toggleFavorite(productId: Long) {
        if (favoriteDao.isFavorite(productId)) {
            favoriteDao.delete(FavoriteEntity(productId = productId))
        } else {
            favoriteDao.insert(FavoriteEntity(productId = productId))
        }
        productsState.value = productsState.value.map { product ->
            if (product.id == productId) {
                product.copy(isFavorite = !product.isFavorite)
            } else {
                product
            }
        }
    }

    override fun observeFavorites(): Flow<List<ProductItem>> {
        return observeProducts().map { products ->
            products.filter { it.isFavorite }
        }
    }

    private suspend fun updateCache(products: List<ProductDto>) {
        val favoriteIds = favoriteDao.getFavoriteIds().toSet()
        val metaByProductId = productMetaDao.getAll().associateBy { it.productId }
        productsState.value = products.toProductItems(favoriteIds, metaByProductId)
    }
}
