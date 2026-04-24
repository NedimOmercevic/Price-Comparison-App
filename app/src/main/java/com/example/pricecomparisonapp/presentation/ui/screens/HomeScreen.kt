package com.example.pricecomparisonapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pricecomparisonapp.data.model.HardcodedData
import com.example.pricecomparisonapp.presentation.ui.components.SectionHeader
import com.example.pricecomparisonapp.presentation.viewmodel.ProductsUiState

@Composable
fun HomeScreen(
    uiState: ProductsUiState,
    onSearchChange: (String) -> Unit,
    onCitySelect: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionHeader("Price Comparison BiH")
        Text("State-driven dashboard overview")

        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = onSearchChange,
            label = { Text("Search by product/store") }
        )

        SectionHeader("Cities")
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

        SectionHeader("Category summary")
        if (uiState.categoryCountByCity.isEmpty()) {
            Text("No items available for this city.")
        } else {
            uiState.categoryCountByCity.forEach { (category, count) ->
                Text("$category: $count")
            }
        }
    }
}
