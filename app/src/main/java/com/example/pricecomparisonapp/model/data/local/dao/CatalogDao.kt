package com.example.pricecomparisonapp.model.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.pricecomparisonapp.model.data.local.entity.ProductCatalogItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogDao {
    @Query(
        """
        SELECT p.id AS productId, p.name AS name, cat.name AS categoryName,
               st.name AS storeName, ct.name AS cityName, po.priceBam AS priceBam,
               EXISTS(SELECT 1 FROM favorites f WHERE f.productId = p.id) AS isFavorite
        FROM products p
        INNER JOIN categories cat ON p.categoryId = cat.id
        INNER JOIN price_offers po ON po.productId = p.id
        INNER JOIN stores st ON po.storeId = st.id
        INNER JOIN cities ct ON st.cityId = ct.id
        ORDER BY p.name
        """
    )
    fun observeCatalog(): Flow<List<ProductCatalogItem>>

    @Query(
        """
        SELECT p.id AS productId, p.name AS name, cat.name AS categoryName,
               st.name AS storeName, ct.name AS cityName, po.priceBam AS priceBam,
               EXISTS(SELECT 1 FROM favorites f WHERE f.productId = p.id) AS isFavorite
        FROM products p
        INNER JOIN categories cat ON p.categoryId = cat.id
        INNER JOIN price_offers po ON po.productId = p.id
        INNER JOIN stores st ON po.storeId = st.id
        INNER JOIN cities ct ON st.cityId = ct.id
        WHERE p.id = :productId
        ORDER BY po.priceBam ASC
        LIMIT 1
        """
    )
    suspend fun getCatalogItem(productId: Long): ProductCatalogItem?
}
