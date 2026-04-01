package com.example.pricecomparisonapp.data.model

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val lowestPriceBam: Double,
    val storeName: String,
    val city: String,
    val isFavorite: Boolean = false
)
