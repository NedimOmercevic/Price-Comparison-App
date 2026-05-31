package com.example.pricecomparisonapp.model.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreateProductRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("category")
    val category: String
)
