package com.example.pricecomparisonapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pricecomparisonapp.presentation.common.ScreenUiState
import com.example.pricecomparisonapp.presentation.ui.components.SectionHeader
import com.example.pricecomparisonapp.presentation.viewmodel.home.HomeSuccessData
import com.example.pricecomparisonapp.presentation.viewmodel.home.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreenStateless(
        uiState = uiState,
        onSearchChange = viewModel::onSearchChange,
        onCitySelect = viewModel::onCitySelect
    )
}

@Composable
fun HomeScreenStateless(
    uiState: ScreenUiState<HomeSuccessData>,
    onSearchChange: (String) -> Unit,
    onCitySelect: (String) -> Unit
) {
    when (uiState) {
        ScreenUiState.Init, ScreenUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
        is ScreenUiState.Error -> {
            Text("Error: ${uiState.message}", modifier = Modifier.padding(16.dp))
        }
        is ScreenUiState.Success -> {
            val data = uiState.data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SectionHeader("Price Comparison BiH")
                OutlinedTextField(
                    value = data.searchQuery,
                    onValueChange = onSearchChange,
                    label = { Text("Search by product/store") }
                )
                SectionHeader("Cities")
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
                SectionHeader("Category summary (${data.selectedCity})")
                if (data.categorySummary.isEmpty()) {
                    Text("No items available.")
                } else {
                    data.categorySummary.forEach { (category, count) ->
                        Text("$category: $count")
                    }
                }
            }
        }
    }
}
