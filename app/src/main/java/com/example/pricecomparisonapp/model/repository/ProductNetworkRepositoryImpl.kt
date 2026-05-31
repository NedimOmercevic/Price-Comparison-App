package com.example.pricecomparisonapp.model.repository

import com.example.pricecomparisonapp.model.data.remote.NetworkException
import com.example.pricecomparisonapp.model.data.remote.api.ProductApiService
import com.example.pricecomparisonapp.model.data.remote.dto.CreateProductRequest
import com.example.pricecomparisonapp.model.data.remote.dto.ProductDto
import com.example.pricecomparisonapp.model.data.remote.dto.UpdateProductRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductNetworkRepositoryImpl @Inject constructor(
    private val productApiService: ProductApiService
) : ProductNetworkRepository {

    override suspend fun getProducts(): Result<List<ProductDto>> = withContext(Dispatchers.IO) {
        runCatching { productApiService.getProducts() }
            .mapFailure { it.toNetworkException("Failed to load products") }
    }

    override suspend fun getProduct(productId: Long): Result<ProductDto> = withContext(Dispatchers.IO) {
        runCatching { productApiService.getProduct(productId.toInt()) }
            .mapFailure { it.toNetworkException("Failed to load product details") }
    }

    override suspend fun createProduct(request: CreateProductRequest): Result<ProductDto> =
        withContext(Dispatchers.IO) {
            runCatching { productApiService.createProduct(request) }
                .mapFailure { it.toNetworkException("Failed to create product") }
        }

    override suspend fun updateProduct(
        productId: Long,
        request: UpdateProductRequest
    ): Result<ProductDto> = withContext(Dispatchers.IO) {
        runCatching { productApiService.updateProduct(productId.toInt(), request) }
            .mapFailure { it.toNetworkException("Failed to update product") }
    }

    override suspend fun deleteProduct(productId: Long): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            productApiService.deleteProduct(productId.toInt())
        }.map { }
            .mapFailure { it.toNetworkException("Failed to delete product") }
    }

    private fun Throwable.toNetworkException(fallbackMessage: String): NetworkException {
        return when (this) {
            is NetworkException -> this
            is HttpException -> NetworkException(
                message = "$fallbackMessage (HTTP ${code()})",
                cause = this
            )
            is IOException -> NetworkException(
                message = "Network error. Check your internet connection.",
                cause = this
            )
            else -> NetworkException(
                message = message ?: fallbackMessage,
                cause = this
            )
        }
    }

    private fun <T> Result<T>.mapFailure(transform: (Throwable) -> NetworkException): Result<T> {
        return fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(transform(it)) }
        )
    }
}
