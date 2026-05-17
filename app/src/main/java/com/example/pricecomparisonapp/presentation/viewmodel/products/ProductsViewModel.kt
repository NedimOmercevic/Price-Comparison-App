package com.example.pricecomparisonapp.presentation.viewmodel.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricecomparisonapp.model.data.ProductItem
import com.example.pricecomparisonapp.model.data.local.util.AppDatabaseInitializer
import com.example.pricecomparisonapp.model.repository.CategoryRepository
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

data class ProductsSuccessData(
    val products: List<ProductItem>,
    val categories: List<String>,
    val selectedCity: String,
    val selectedCategory: String?,
    val isFilterActive: Boolean
)

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val filtersRepository: FiltersRepository,
    private val databaseInitializer: AppDatabaseInitializer
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScreenUiState<ProductsSuccessData>>(ScreenUiState.Init)
    val uiState: StateFlow<ScreenUiState<ProductsSuccessData>> = _uiState.asStateFlow()

    init {
        observeProducts()
    }

    private fun observeProducts() {
        viewModelScope.launch {
            _uiState.value = ScreenUiState.Loading
            try {
                databaseInitializer.ensureReady()
                combine(
                    productRepository.observeProducts(),
                    categoryRepository.observeCategoryNames(),
                    filtersRepository.selectedCity,
                    filtersRepository.selectedCategory,
                    filtersRepository.searchQuery
                ) { products, categories, city, category, search ->
                    val filtered = products
                        .filter { it.cityName == city }
                        .filter { category == null || it.categoryName == category }
                        .filter {
                            search.isBlank() ||
                                it.name.contains(search, true) ||
                                it.storeName.contains(search, true)
                        }
                    ProductsSuccessData(
                        products = filtered,
                        categories = categories,
                        selectedCity = city,
                        selectedCategory = category,
                        isFilterActive = category != null || search.isNotBlank()
                    )
                }.collect { data ->
                    _uiState.value = ScreenUiState.Success(data)
                }
            } catch (e: Exception) {
                _uiState.value = ScreenUiState.Error(e.message ?: "Failed to load products")
            }
        }
    }

    fun onCategorySelect(category: String?) {
        filtersRepository.setCategory(category)
    }

    fun onToggleFavorite(productId: Long) {
        viewModelScope.launch {
            productRepository.toggleFavorite(productId)
        }
    }
}
