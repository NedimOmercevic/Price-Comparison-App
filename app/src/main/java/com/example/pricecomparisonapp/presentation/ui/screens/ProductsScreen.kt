package com.example.pricecomparisonapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pricecomparisonapp.data.model.HardcodedData
import com.example.pricecomparisonapp.presentation.ui.components.FilterChipRow
import com.example.pricecomparisonapp.presentation.ui.components.ProductCard
import com.example.pricecomparisonapp.presentation.ui.components.SectionHeader
import com.example.pricecomparisonapp.presentation.viewmodel.ProductsUiState

@Composable
fun ProductsScreen(
    uiState: ProductsUiState,
    onCategorySelect: (String?) -> Unit,
    onProductClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SectionHeader("Products in ${uiState.selectedCity}")
        FilterChipRow(
            items = HardcodedData.categories,
            selected = uiState.selectedCategory,
            onSelect = onCategorySelect
        )
        if (uiState.filteredProducts.isEmpty()) {
            Text("No items available.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiState.filteredProducts) { product ->
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
