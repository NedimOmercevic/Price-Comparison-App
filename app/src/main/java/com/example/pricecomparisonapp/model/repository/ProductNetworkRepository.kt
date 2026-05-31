package com.example.pricecomparisonapp.model.repository

import com.example.pricecomparisonapp.model.data.remote.dto.CreateProductRequest
import com.example.pricecomparisonapp.model.data.remote.dto.ProductDto
import com.example.pricecomparisonapp.model.data.remote.dto.UpdateProductRequest

interface ProductNetworkRepository {
    suspend fun getProducts(): Result<List<ProductDto>>
    suspend fun getProduct(productId: Long): Result<ProductDto>
    suspend fun createProduct(request: CreateProductRequest): Result<ProductDto>
    suspend fun updateProduct(productId: Long, request: UpdateProductRequest): Result<ProductDto>
    suspend fun deleteProduct(productId: Long): Result<Unit>
}
