package com.example.pricecomparisonapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pricecomparisonapp.data.model.Product
import com.example.pricecomparisonapp.ui.state.AppUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        AppUiState(
            products = sampleProducts()
        )
    )
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun selectCity(city: String) {
        _uiState.update { it.copy(selectedCity = city) }
    }

    fun selectProduct(product: Product) {
        _uiState.update { it.copy(selectedProduct = product) }
    }

    fun updateNameInput(value: String) {
        _uiState.update { it.copy(nameInput = value, formSubmitted = false) }
    }

    fun updatePriceInput(value: String) {
        _uiState.update { it.copy(priceInput = value, formSubmitted = false) }
    }

    fun updateCategoryInput(value: String) {
        _uiState.update { it.copy(categoryInput = value, formSubmitted = false) }
    }

    fun updateStoreInput(value: String) {
        _uiState.update { it.copy(storeInput = value, formSubmitted = false) }
    }

    fun submitProduct() {
        val current = _uiState.value
        if (!current.isFormValid) {
            _uiState.update { it.copy(formSubmitted = true) }
            return
        }
        val newProduct = Product(
            id = current.products.size + 1,
            name = current.nameInput.trim(),
            category = current.categoryInput.trim(),
            lowestPriceBam = current.priceInput.toDouble(),
            storeName = current.storeInput.trim(),
            city = current.selectedCity
        )
        _uiState.update {
            it.copy(
                products = it.products + newProduct,
                selectedProduct = newProduct,
                nameInput = "",
                priceInput = "",
                categoryInput = "",
                storeInput = "",
                formSubmitted = false
            )
        }
    }

    fun toggleFavorite(productId: Int) {
        _uiState.update { state ->
            state.copy(
                products = state.products.map { product ->
                    if (product.id == productId) {
                        product.copy(isFavorite = !product.isFavorite)
                    } else {
                        product
                    }
                }
            )
        }
    }

    private fun sampleProducts(): List<Product> {
        return listOf(
            Product(1, "Milk 1L", "Dairy", 2.30, "Bingo", "Sarajevo"),
            Product(2, "Bread", "Bakery", 1.70, "Konzum", "Tuzla"),
            Product(3, "Coffee 500g", "Beverages", 8.90, "Mercator", "Mostar")
        )
    }
}
