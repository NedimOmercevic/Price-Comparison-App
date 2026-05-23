package com.example.pricecomparisonapp.model.repository.mappers

import com.example.pricecomparisonapp.model.data.ProductItem
import com.example.pricecomparisonapp.model.data.local.entity.ProductCatalogItem

fun ProductCatalogItem.toProductItem(): ProductItem {
    return ProductItem(
        id = productId,
        name = name,
        categoryName = categoryName,
        storeName = storeName,
        cityName = cityName,
        priceBam = priceBam,
        isFavorite = isFavorite
    )
}

fun List<ProductCatalogItem>.toProductItems(): List<ProductItem> {
    return map { it.toProductItem() }
        .groupBy { it.id }
        .map { (_, items) ->
            items.minBy { it.priceBam }
        }
        .sortedBy { it.name }
}
