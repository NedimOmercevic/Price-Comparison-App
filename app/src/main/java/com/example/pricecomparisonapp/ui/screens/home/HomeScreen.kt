package com.example.pricecomparisonapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pricecomparisonapp.ui.screens.home.components.CitySelector
import com.example.pricecomparisonapp.ui.screens.home.components.HomeHeader
import com.example.pricecomparisonapp.ui.state.AppUiState

@Composable
fun HomeScreen(
    uiState: AppUiState,
    onSelectCity: (String) -> Unit,
    onGoToList: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HomeHeader()
        CitySelector(
            selectedCity = uiState.selectedCity,
            onSelectCity = onSelectCity
        )
        Text("Tracked products: ${uiState.products.size}")
        Button(onClick = onGoToList) {
            Text("Open product overview")
        }
    }
}
