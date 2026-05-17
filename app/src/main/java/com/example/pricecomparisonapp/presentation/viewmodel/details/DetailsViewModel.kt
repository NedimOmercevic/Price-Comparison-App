package com.example.pricecomparisonapp.presentation.viewmodel.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricecomparisonapp.model.data.ProductItem
import com.example.pricecomparisonapp.model.repository.ProductRepository
import com.example.pricecomparisonapp.presentation.common.ScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailsSuccessData(
    val product: ProductItem,
    val origin: String
)

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val productId: Long = savedStateHandle.get<Long>("productId")
        ?: savedStateHandle.get<Int>("productId")?.toLong()
        ?: -1L
    private val origin: String = savedStateHandle.get<String>("origin").orEmpty()

    private val _uiState = MutableStateFlow<ScreenUiState<DetailsSuccessData>>(ScreenUiState.Init)
    val uiState: StateFlow<ScreenUiState<DetailsSuccessData>> = _uiState.asStateFlow()

    init {
        loadDetails()
    }

    private fun loadDetails() {
        viewModelScope.launch {
            _uiState.value = ScreenUiState.Loading
            try {
                val product = productRepository.getProduct(productId)
                if (product == null) {
                    _uiState.value = ScreenUiState.Error("Product not found")
                } else {
                    _uiState.value = ScreenUiState.Success(DetailsSuccessData(product, origin))
                }
            } catch (e: Exception) {
                _uiState.value = ScreenUiState.Error(e.message ?: "Failed to load details")
            }
        }
    }

    fun onToggleFavorite() {
        viewModelScope.launch {
            productRepository.toggleFavorite(productId)
            loadDetails()
        }
    }

    fun onDeleteProduct() {
        viewModelScope.launch {
            productRepository.deleteProduct(productId)
        }
    }
}
