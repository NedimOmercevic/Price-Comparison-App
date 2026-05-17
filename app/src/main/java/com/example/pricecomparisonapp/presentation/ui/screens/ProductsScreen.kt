package com.example.pricecomparisonapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pricecomparisonapp.presentation.common.ScreenUiState
import com.example.pricecomparisonapp.presentation.ui.components.FilterChipRow
import com.example.pricecomparisonapp.presentation.ui.components.ProductCard
import com.example.pricecomparisonapp.presentation.ui.components.SectionHeader
import com.example.pricecomparisonapp.presentation.viewmodel.products.ProductsSuccessData
import com.example.pricecomparisonapp.presentation.viewmodel.products.ProductsViewModel

@Composable
fun ProductsScreen(
    onProductClick: (Long) -> Unit,
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ProductsScreenStateless(
        uiState = uiState,
        onCategorySelect = viewModel::onCategorySelect,
        onProductClick = onProductClick,
        onToggleFavorite = viewModel::onToggleFavorite
    )
}

@Composable
fun ProductsScreenStateless(
    uiState: ScreenUiState<ProductsSuccessData>,
    onCategorySelect: (String?) -> Unit,
    onProductClick: (Long) -> Unit,
    onToggleFavorite: (Long) -> Unit
) {
    when (uiState) {
        ScreenUiState.Init, ScreenUiState.Loading -> CircularProgressIndicator(Modifier.padding(16.dp))
        is ScreenUiState.Error -> Text("Error: ${uiState.message}", Modifier.padding(16.dp))
        is ScreenUiState.Success -> {
            val data = uiState.data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SectionHeader("Products in ${data.selectedCity}")
                Text(
                    text = "Saved offers are stored in Room and filtered by selected city.",
                    maxLines = 2
                )
                FilterChipRow(
                    items = data.categories,
                    selected = data.selectedCategory,
                    onSelect = onCategorySelect
                )
                if (data.products.isEmpty()) {
                    Text("No items available.")
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(data.products, key = { it.id }) { product ->
                            ProductCard(
                                product = product,
                                onOpen = { onProductClick(product.id) },
                                onToggleFavorite = { onToggleFavorite(product.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
