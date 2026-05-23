package com.example.pricecomparisonapp.presentation.viewmodel.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricecomparisonapp.model.data.ProductItem
import com.example.pricecomparisonapp.model.repository.CityRepository
import com.example.pricecomparisonapp.model.repository.FiltersRepository
import com.example.pricecomparisonapp.model.repository.ProductRepository
import com.example.pricecomparisonapp.presentation.common.ScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FavoritesSuccessData(
    val favorites: List<ProductItem>,
    val cities: List<String>,
    val selectedCity: String
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cityRepository: CityRepository,
    private val filtersRepository: FiltersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScreenUiState<FavoritesSuccessData>>(ScreenUiState.Init)
    val uiState: StateFlow<ScreenUiState<FavoritesSuccessData>> = _uiState.asStateFlow()

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            _uiState.value = ScreenUiState.Loading
            try {
                combine(
                    productRepository.observeFavorites(),
                    cityRepository.observeCityNames(),
                    filtersRepository.selectedCity
                ) { favorites, cities, city ->
                    FavoritesSuccessData(
                        favorites = favorites.filter { it.cityName == city },
                        cities = cities,
                        selectedCity = city
                    )
                }.collect { data ->
                    _uiState.value = ScreenUiState.Success(data)
                }
            } catch (e: Exception) {
                _uiState.value = ScreenUiState.Error(e.message ?: "Failed to load favorites")
            }
        }
    }

    fun onCitySelect(city: String) {
        filtersRepository.setCity(city)
    }

    fun onToggleFavorite(productId: Long) {
        viewModelScope.launch {
            productRepository.toggleFavorite(productId)
        }
    }
}
