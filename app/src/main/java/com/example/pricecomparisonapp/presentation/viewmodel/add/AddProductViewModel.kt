package com.example.pricecomparisonapp.presentation.viewmodel.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricecomparisonapp.model.data.local.util.AppDatabaseInitializer
import com.example.pricecomparisonapp.model.repository.FiltersRepository
import com.example.pricecomparisonapp.model.repository.ProductNetworkRepository
import com.example.pricecomparisonapp.model.repository.ProductRepository
import com.example.pricecomparisonapp.model.repository.mappers.buildCreateProductRequest
import com.example.pricecomparisonapp.presentation.common.ScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddProductFormData(
    val nameInput: String = "",
    val categoryInput: String = "",
    val storeInput: String = "",
    val priceInput: String = "",
    val selectedCity: String = "Sarajevo",
    val formSubmitted: Boolean = false
) {
    val isNameValid: Boolean get() = nameInput.trim().length >= 2
    val isCategoryValid: Boolean get() = categoryInput.trim().isNotBlank()
    val isStoreValid: Boolean get() = storeInput.trim().isNotBlank()
    val isPriceValid: Boolean get() = priceInput.toDoubleOrNull()?.let { it > 0 } == true
    val isFormValid: Boolean get() = isNameValid && isCategoryValid && isStoreValid && isPriceValid
}

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productNetworkRepository: ProductNetworkRepository,
    private val productRepository: ProductRepository,
    private val filtersRepository: FiltersRepository,
    private val databaseInitializer: AppDatabaseInitializer
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScreenUiState<AddProductFormData>>(ScreenUiState.Init)
    val uiState: StateFlow<ScreenUiState<AddProductFormData>> = _uiState.asStateFlow()

    init {
        _uiState.value = ScreenUiState.Success(AddProductFormData())
        viewModelScope.launch {
            databaseInitializer.ensureReady()
            filtersRepository.selectedCity.collect { city ->
                updateForm { it.copy(selectedCity = city) }
            }
        }
    }

    fun onNameChange(value: String) = updateForm { it.copy(nameInput = value, formSubmitted = false) }
    fun onCategoryChange(value: String) = updateForm { it.copy(categoryInput = value, formSubmitted = false) }
    fun onStoreChange(value: String) = updateForm { it.copy(storeInput = value, formSubmitted = false) }
    fun onPriceChange(value: String) = updateForm { it.copy(priceInput = value, formSubmitted = false) }

    fun submit(onSuccess: () -> Unit) {
        val form = (uiState.value as? ScreenUiState.Success)?.data ?: return
        if (!form.isFormValid) {
            updateForm { it.copy(formSubmitted = true) }
            return
        }
        viewModelScope.launch {
            _uiState.value = ScreenUiState.Loading
            try {
                databaseInitializer.ensureReady()
                val request = buildCreateProductRequest(
                    name = form.nameInput,
                    categoryName = form.categoryInput,
                    storeName = form.storeInput,
                    cityName = form.selectedCity,
                    priceBam = form.priceInput.toDouble()
                )
                productNetworkRepository.createProduct(request)
                    .onSuccess { createdProduct ->
                        productRepository.saveProductMeta(
                            productId = createdProduct.id.toLong(),
                            storeName = form.storeInput,
                            cityName = form.selectedCity
                        )
                        productRepository.refreshProducts()
                        _uiState.value = ScreenUiState.Success(AddProductFormData(selectedCity = form.selectedCity))
                        onSuccess()
                    }
                    .onFailure { error ->
                        _uiState.value = ScreenUiState.Error(
                            error.message ?: "Failed to save product"
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = ScreenUiState.Error(e.message ?: "Failed to save product")
            }
        }
    }

    private fun updateForm(transform: (AddProductFormData) -> AddProductFormData) {
        val current = (_uiState.value as? ScreenUiState.Success)?.data ?: AddProductFormData()
        _uiState.value = ScreenUiState.Success(transform(current))
    }
}
