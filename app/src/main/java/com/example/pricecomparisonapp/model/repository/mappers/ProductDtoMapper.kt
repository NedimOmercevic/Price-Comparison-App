package com.example.pricecomparisonapp.model.repository.mappers

import com.example.pricecomparisonapp.model.data.ProductItem
import com.example.pricecomparisonapp.model.data.local.entity.ProductMetaEntity
import com.example.pricecomparisonapp.model.data.remote.dto.CreateProductRequest
import com.example.pricecomparisonapp.model.data.remote.dto.ProductDto
import com.example.pricecomparisonapp.model.data.remote.dto.UpdateProductRequest

private const val DEFAULT_STORE = "Global Market"
private const val DEFAULT_CITY = "Sarajevo"
private const val PLACEHOLDER_IMAGE = "https://via.placeholder.com/150"

fun ProductDto.toProductItem(
    isFavorite: Boolean,
    meta: ProductMetaEntity?
): ProductItem {
    return ProductItem(
        id = id.toLong(),
        name = title,
        categoryName = category.replaceFirstChar { it.uppercase() },
        storeName = meta?.storeName ?: DEFAULT_STORE,
        cityName = meta?.cityName ?: DEFAULT_CITY,
        priceBam = price,
        isFavorite = isFavorite
    )
}

fun List<ProductDto>.toProductItems(
    favoriteIds: Set<Long>,
    metaByProductId: Map<Long, ProductMetaEntity>
): List<ProductItem> {
    return map { dto ->
        dto.toProductItem(
            isFavorite = favoriteIds.contains(dto.id.toLong()),
            meta = metaByProductId[dto.id.toLong()]
        )
    }.sortedBy { it.name }
}

fun buildCreateProductRequest(
    name: String,
    categoryName: String,
    storeName: String,
    cityName: String,
    priceBam: Double
): CreateProductRequest {
    return CreateProductRequest(
        title = name.trim(),
        price = priceBam,
        description = "Store: ${storeName.trim()}, City: ${cityName.trim()}",
        image = PLACEHOLDER_IMAGE,
        category = categoryName.trim()
    )
}

fun buildUpdateProductRequest(
    name: String,
    categoryName: String,
    storeName: String,
    cityName: String,
    priceBam: Double
): UpdateProductRequest {
    return UpdateProductRequest(
        title = name.trim(),
        price = priceBam,
        description = "Store: ${storeName.trim()}, City: ${cityName.trim()}",
        image = PLACEHOLDER_IMAGE,
        category = categoryName.trim()
    )
}
