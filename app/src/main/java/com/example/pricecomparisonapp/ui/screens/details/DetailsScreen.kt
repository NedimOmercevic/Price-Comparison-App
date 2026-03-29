package com.example.pricecomparisonapp.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pricecomparisonapp.ui.screens.details.components.DetailsCard
import com.example.pricecomparisonapp.ui.state.AppUiState

@Composable
fun DetailsScreen(uiState: AppUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Selected product")
        val product = uiState.selectedProduct
        if (product == null) {
            Text("Please select a product from the list.")
        } else {
            DetailsCard(product = product)
        }
    }
}
