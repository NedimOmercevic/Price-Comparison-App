package com.example.pricecomparisonapp.presentation.common

sealed interface ScreenUiState<out T> {
    data object Init : ScreenUiState<Nothing>
    data object Loading : ScreenUiState<Nothing>
    data class Success<T>(val data: T) : ScreenUiState<T>
    data class Error(val message: String) : ScreenUiState<Nothing>
}
