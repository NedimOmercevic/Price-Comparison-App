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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pricecomparisonapp.presentation.common.ScreenUiState
import com.example.pricecomparisonapp.presentation.ui.components.ProductCard
import com.example.pricecomparisonapp.presentation.ui.components.SectionHeader
import com.example.pricecomparisonapp.presentation.viewmodel.favorites.FavoritesSuccessData
import com.example.pricecomparisonapp.presentation.viewmodel.favorites.FavoritesViewModel

@Composable
fun FavoritesScreen(
    onOpenDetails: (Long) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    FavoritesScreenStateless(
        uiState = uiState,
        onCitySelect = viewModel::onCitySelect,
        onOpenDetails = onOpenDetails,
        onToggleFavorite = viewModel::onToggleFavorite
    )
}

@Composable
fun FavoritesScreenStateless(
    uiState: ScreenUiState<FavoritesSuccessData>,
    onCitySelect: (String) -> Unit,
    onOpenDetails: (Long) -> Unit,
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
                SectionHeader("Favorites")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(data.cities) { city ->
                        AssistChip(
                            onClick = { onCitySelect(city) },
                            label = { Text(if (data.selectedCity == city) "$city ✓" else city) }
                        )
                    }
                }
                if (data.favorites.isEmpty()) {
                    Text("No items available.")
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(data.favorites, key = { it.id }) { product ->
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
    }
}
