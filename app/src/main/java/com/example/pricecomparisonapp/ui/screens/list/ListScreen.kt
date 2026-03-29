package com.example.pricecomparisonapp.ui.screens.list

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
import com.example.pricecomparisonapp.data.model.Product
import com.example.pricecomparisonapp.ui.screens.list.components.EmptyListState
import com.example.pricecomparisonapp.ui.screens.list.components.ProductListItem
import com.example.pricecomparisonapp.ui.state.AppUiState

@Composable
fun ListScreen(
    uiState: AppUiState,
    onSelectProduct: (Product) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    val filteredProducts = uiState.products.filter { it.city == uiState.selectedCity }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Products in ${uiState.selectedCity}")
        if (filteredProducts.isEmpty()) {
            EmptyListState()
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filteredProducts) { product ->
                    ProductListItem(
                        product = product,
                        onOpenDetails = { onSelectProduct(product) },
                        onToggleFavorite = { onToggleFavorite(product.id) }
                    )
                }
            }
        }
    }
}
