package com.example.pricecomparisonapp.ui.state

import com.example.pricecomparisonapp.data.model.Product

data class AppUiState(
    val products: List<Product> = emptyList(),
    val selectedCity: String = "Sarajevo",
    val selectedProduct: Product? = null,
    val nameInput: String = "",
    val priceInput: String = "",
    val categoryInput: String = "",
    val storeInput: String = "",
    val formSubmitted: Boolean = false
) {
    val isNameValid: Boolean = nameInput.trim().length >= 2
    val isPriceValid: Boolean = priceInput.toDoubleOrNull()?.let { it > 0.0 } == true
    val isCategoryValid: Boolean = categoryInput.isNotBlank()
    val isStoreValid: Boolean = storeInput.isNotBlank()
    val isFormValid: Boolean = isNameValid && isPriceValid && isCategoryValid && isStoreValid
}
