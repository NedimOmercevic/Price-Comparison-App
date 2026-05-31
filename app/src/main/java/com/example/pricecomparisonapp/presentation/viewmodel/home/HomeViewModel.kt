package com.example.pricecomparisonapp.presentation.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricecomparisonapp.model.data.local.util.AppDatabaseInitializer
import com.example.pricecomparisonapp.model.repository.CityRepository
import com.example.pricecomparisonapp.model.repository.FiltersRepository
import com.example.pricecomparisonapp.model.repository.ProductNetworkRepository
import com.example.pricecomparisonapp.model.repository.ProductRepository
import com.example.pricecomparisonapp.presentation.common.ScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeSuccessData(
    val cities: List<String>,
    val selectedCity: String,
    val searchQuery: String,
    val categorySummary: Map<String, Int>
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productNetworkRepository: ProductNetworkRepository,
    private val productRepository: ProductRepository,
    private val cityRepository: CityRepository,
    private val filtersRepository: FiltersRepository,
    private val databaseInitializer: AppDatabaseInitializer
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScreenUiState<HomeSuccessData>>(ScreenUiState.Init)
    val uiState: StateFlow<ScreenUiState<HomeSuccessData>> = _uiState.asStateFlow()

    init {
        observeHome()
    }

    private fun observeHome() {
        viewModelScope.launch {
            _uiState.value = ScreenUiState.Loading
            try {
                databaseInitializer.ensureReady()
                productNetworkRepository.getProducts()
                    .onSuccess { products ->
                        productRepository.syncFromNetwork(products)
                    }
                    .onFailure { error ->
                        _uiState.value = ScreenUiState.Error(
                            error.message ?: "Failed to load home data from network"
                        )
                        return@launch
                    }

                combine(
                    cityRepository.observeCityNames(),
                    productRepository.observeProducts(),
                    filtersRepository.selectedCity,
                    filtersRepository.searchQuery
                ) { cities, products, selectedCity, searchQuery ->
                    val cityProducts = products.filter { it.cityName == selectedCity }
                    val filtered = if (searchQuery.isBlank()) {
                        cityProducts
                    } else {
                        cityProducts.filter {
                            it.name.contains(searchQuery, true) ||
                                it.storeName.contains(searchQuery, true)
                        }
                    }
                    val summary = filtered.groupBy { it.categoryName }.mapValues { (_, list) -> list.size }
                    HomeSuccessData(
                        cities = cities,
                        selectedCity = selectedCity,
                        searchQuery = searchQuery,
                        categorySummary = summary
                    )
                }.collect { data ->
                    _uiState.value = ScreenUiState.Success(data)
                }
            } catch (e: Exception) {
                _uiState.value = ScreenUiState.Error(e.message ?: "Failed to load home data")
            }
        }
    }

    fun onSearchChange(query: String) {
        filtersRepository.setSearchQuery(query)
    }

    fun onCitySelect(city: String) {
        filtersRepository.setCity(city)
    }
}
