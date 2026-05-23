package com.example.pricecomparisonapp.model.data.local.util

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
import javax.inject.Inject

class DatabaseSeeder @Inject constructor(
    private val cityDao: CityDao,
    private val storeDao: StoreDao,
    private val categoryDao: CategoryDao,
    private val productDao: ProductDao,
    private val priceOfferDao: PriceOfferDao,
    private val favoriteDao: FavoriteDao
) {
    suspend fun seedIfEmpty() {
        if (cityDao.count() > 0) return

        val cityIds = cityDao.insertAll(
            listOf(
                CityEntity(name = "Sarajevo"),
                CityEntity(name = "Tuzla"),
                CityEntity(name = "Mostar")
            )
        )
        val sarajevoId = cityIds[0]
        val tuzlaId = cityIds[1]
        val mostarId = cityIds[2]

        val categoryIds = categoryDao.insertAll(
            listOf(
                CategoryEntity(name = "Dairy"),
                CategoryEntity(name = "Bakery"),
                CategoryEntity(name = "Beverages")
            )
        )
        val dairyId = categoryIds[0]
        val bakeryId = categoryIds[1]
        val beveragesId = categoryIds[2]

        val bingoSarajevo = storeDao.insert(StoreEntity(name = "Bingo", cityId = sarajevoId))
        val konzumTuzla = storeDao.insert(StoreEntity(name = "Konzum", cityId = tuzlaId))
        val mercatorMostar = storeDao.insert(StoreEntity(name = "Mercator", cityId = mostarId))

        val milkId = productDao.insert(ProductEntity(name = "Milk 1L", categoryId = dairyId))
        val breadId = productDao.insert(ProductEntity(name = "Bread", categoryId = bakeryId))
        val coffeeId = productDao.insert(ProductEntity(name = "Coffee 500g", categoryId = beveragesId))

        priceOfferDao.insert(PriceOfferEntity(productId = milkId, storeId = bingoSarajevo, priceBam = 2.30))
        priceOfferDao.insert(PriceOfferEntity(productId = breadId, storeId = konzumTuzla, priceBam = 1.70))
        priceOfferDao.insert(PriceOfferEntity(productId = coffeeId, storeId = mercatorMostar, priceBam = 8.90))

        favoriteDao.insert(FavoriteEntity(productId = milkId))
    }
}
