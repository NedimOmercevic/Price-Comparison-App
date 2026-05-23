package com.example.pricecomparisonapp.model.data

data class ProductItem(
    val id: Long,
    val name: String,
    val categoryName: String,
    val storeName: String,
    val cityName: String,
    val priceBam: Double,
    val isFavorite: Boolean
)
