package com.example.pricecomparisonapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pricecomparisonapp.data.model.HardcodedData
import com.example.pricecomparisonapp.presentation.ui.components.ProductCard
import com.example.pricecomparisonapp.presentation.ui.components.SectionHeader
import com.example.pricecomparisonapp.presentation.viewmodel.ProductsUiState

@Composable
fun FavoritesScreen(
    uiState: ProductsUiState,
    onCitySelect: (String) -> Unit,
    onOpenDetails: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SectionHeader("Favorites")
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(HardcodedData.cities) { city ->
                AssistChip(
                    onClick = { onCitySelect(city) },
                    label = { Text(if (uiState.selectedCity == city) "$city ✓" else city) }
                )
            }
        }
        if (uiState.favoriteProducts.isEmpty()) {
            Text("No items available.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiState.favoriteProducts.filter { it.city == uiState.selectedCity }) { product ->
                    ProductCard(
                        product = product,
                        onOpen = { onOpenDetails(product.id) },
                        onToggleFavorite = { onToggleFavorite(product.id) }
                    )
                }
            }
        }
    }
}
