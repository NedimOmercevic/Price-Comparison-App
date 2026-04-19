package com.example.pricecomparisonapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pricecomparisonapp.data.model.HardcodedData
import com.example.pricecomparisonapp.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        ProductsUiState(allProducts = HardcodedData.products)
    )
    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun setCity(city: String) {
        _uiState.update { it.copy(selectedCity = city, selectedCategory = null) }
    }

    fun setCategory(category: String?) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun toggleFavorite(productId: Int) {
        _uiState.update { state ->
            state.copy(
                allProducts = state.allProducts.map { product ->
                    if (product.id == productId) {
                        product.copy(isFavorite = !product.isFavorite)
                    } else {
                        product
                    }
                }
            )
        }
    }

    fun selectProduct(productId: Int) {
        _uiState.update { it.copy(selectedProductId = productId) }
    }

    fun updateNameInput(value: String) {
        _uiState.update { it.copy(nameInput = value, formSubmitted = false) }
    }

    fun updateCategoryInput(value: String) {
        _uiState.update { it.copy(categoryInput = value, formSubmitted = false) }
    }

    fun updateStoreInput(value: String) {
        _uiState.update { it.copy(storeInput = value, formSubmitted = false) }
    }

    fun updatePriceInput(value: String) {
        _uiState.update { it.copy(priceInput = value, formSubmitted = false) }
    }

    fun submitProduct(): Boolean {
        val state = _uiState.value
        if (!state.isFormValid) {
            _uiState.update { it.copy(formSubmitted = true) }
            return false
        }

        val newProduct = Product(
            id = state.allProducts.maxOfOrNull { it.id }?.plus(1) ?: 1,
            name = state.nameInput.trim(),
            category = state.categoryInput.trim(),
            lowestPriceBam = state.priceInput.toDouble(),
            storeName = state.storeInput.trim(),
            city = state.selectedCity
        )

        _uiState.update {
            it.copy(
                allProducts = it.allProducts + newProduct,
                selectedProductId = newProduct.id,
                nameInput = "",
                categoryInput = "",
                storeInput = "",
                priceInput = "",
                formSubmitted = false
            )
        }
        return true
    }
}
