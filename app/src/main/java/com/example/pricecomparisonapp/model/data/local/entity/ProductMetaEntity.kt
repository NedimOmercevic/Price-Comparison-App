package com.example.pricecomparisonapp.model.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_meta")
data class ProductMetaEntity(
    @PrimaryKey
    val productId: Long,
    val storeName: String,
    val cityName: String
)
