package com.example.pricecomparisonapp.model.data.remote.api

import com.example.pricecomparisonapp.model.data.remote.dto.CreateProductRequest
import com.example.pricecomparisonapp.model.data.remote.dto.ProductDto
import com.example.pricecomparisonapp.model.data.remote.dto.UpdateProductRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApiService {

    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): ProductDto

    @POST("products")
    suspend fun createProduct(@Body request: CreateProductRequest): ProductDto

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body request: UpdateProductRequest
    ): ProductDto

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): ProductDto
}
