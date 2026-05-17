package com.example.pricecomparisonapp.model.repository

import com.example.pricecomparisonapp.model.data.ProductItem
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
import com.example.pricecomparisonapp.model.repository.mappers.toProductItem
import com.example.pricecomparisonapp.model.repository.mappers.toProductItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val catalogDao: CatalogDao,
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao,
    private val cityDao: CityDao,
    private val storeDao: StoreDao,
    private val priceOfferDao: PriceOfferDao,
    private val favoriteDao: FavoriteDao
) : ProductRepository {

    override fun observeProducts(): Flow<List<ProductItem>> {
        return catalogDao.observeCatalog().map { it.toProductItems() }
    }

    override suspend fun getProduct(productId: Long): ProductItem? = withContext(Dispatchers.IO) {
        catalogDao.getCatalogItem(productId)?.toProductItem()
    }

    override suspend fun addProduct(
        name: String,
        categoryName: String,
        storeName: String,
        cityName: String,
        priceBam: Double
    ): Long = withContext(Dispatchers.IO) {
        val categoryId = categoryDao.getByName(categoryName)?.id
            ?: categoryDao.insertAll(listOf(CategoryEntity(name = categoryName))).first()

        val cityId = cityDao.getByName(cityName)?.id
            ?: cityDao.insertAll(listOf(CityEntity(name = cityName))).first()

        val storeId = storeDao.getByNameAndCity(storeName, cityId)
            ?: storeDao.insert(StoreEntity(name = storeName, cityId = cityId))

        val productId = productDao.insert(
            ProductEntity(name = name.trim(), categoryId = categoryId)
        )
        priceOfferDao.insert(
            PriceOfferEntity(productId = productId, storeId = storeId, priceBam = priceBam)
        )
        productId
    }

    override suspend fun updateProductName(productId: Long, name: String) = withContext(Dispatchers.IO) {
        val product = productDao.getById(productId) ?: return@withContext
        productDao.update(product.copy(name = name.trim()))
    }

    override suspend fun deleteProduct(productId: Long) = withContext(Dispatchers.IO) {
        val product = productDao.getById(productId) ?: return@withContext
        productDao.delete(product)
    }

    override suspend fun toggleFavorite(productId: Long) = withContext(Dispatchers.IO) {
        if (favoriteDao.isFavorite(productId)) {
            favoriteDao.delete(FavoriteEntity(productId = productId))
        } else {
            favoriteDao.insert(FavoriteEntity(productId = productId))
        }
    }

    override fun observeFavorites(): Flow<List<ProductItem>> {
        return observeProducts().map { products -> products.filter { it.isFavorite } }
    }
}
