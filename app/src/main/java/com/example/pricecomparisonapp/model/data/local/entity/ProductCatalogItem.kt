package com.example.pricecomparisonapp.model.data.local.entity

data class ProductCatalogItem(
    val productId: Long,
    val name: String,
    val categoryName: String,
    val storeName: String,
    val cityName: String,
    val priceBam: Double,
    val isFavorite: Boolean
)
