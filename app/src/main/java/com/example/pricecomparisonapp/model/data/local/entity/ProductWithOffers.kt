package com.example.pricecomparisonapp.model.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ProductWithOffers(
    @Embedded val product: ProductEntity,
    @Relation(parentColumn = "id", entityColumn = "productId")
    val offers: List<PriceOfferEntity>
)
