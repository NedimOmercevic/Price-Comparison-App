package com.example.pricecomparisonapp.model.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CityWithStores(
    @Embedded val city: CityEntity,
    @Relation(parentColumn = "id", entityColumn = "cityId")
    val stores: List<StoreEntity>
)
