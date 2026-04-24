package com.example.pricecomparisonapp.presentation.viewmodel

import com.example.pricecomparisonapp.data.model.Product

data class ProductsUiState(
    val allProducts: List<Product> = emptyList(),
    val selectedCity: String = "Sarajevo",
    val selectedCategory: String? = null,
    val searchQuery: String = "",
    val selectedProductId: Int? = null,
    val nameInput: String = "",
    val categoryInput: String = "",
    val storeInput: String = "",
    val priceInput: String = "",
    val formSubmitted: Boolean = false
) {
    val filteredProducts: List<Product>
        get() = allProducts
            .asSequence()
            .filter { it.city == selectedCity }
            .filter { selectedCategory == null || it.category == selectedCategory }
            .filter { product ->
                if (searchQuery.isBlank()) {
                    true
                } else {
                    product.name.contains(searchQuery, ignoreCase = true) ||
                        product.storeName.contains(searchQuery, ignoreCase = true)
                }
            }
            .toList()

    val favoriteProducts: List<Product>
        get() = allProducts.filter { it.isFavorite }

    val categoryCountByCity: Map<String, Int>
        get() = allProducts
            .filter { it.city == selectedCity }
            .groupBy { it.category }
            .mapValues { (_, products) -> products.size }

    val isFilterActive: Boolean
        get() = selectedCategory != null || searchQuery.isNotBlank()

    val isNameValid: Boolean
        get() = nameInput.trim().length >= 2

    val isCategoryValid: Boolean
        get() = categoryInput.trim().isNotBlank()

    val isStoreValid: Boolean
        get() = storeInput.trim().isNotBlank()

    val isPriceValid: Boolean
        get() = priceInput.toDoubleOrNull()?.let { it > 0.0 } == true

    val isFormValid: Boolean
        get() = isNameValid && isCategoryValid && isStoreValid && isPriceValid

    val selectedProduct: Product?
        get() = allProducts.firstOrNull { it.id == selectedProductId }
}
