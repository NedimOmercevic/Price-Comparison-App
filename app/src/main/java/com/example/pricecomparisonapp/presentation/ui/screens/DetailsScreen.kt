package com.example.pricecomparisonapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pricecomparisonapp.presentation.ui.components.SectionHeader
import com.example.pricecomparisonapp.presentation.viewmodel.ProductsUiState

@Composable
fun DetailsScreen(
    uiState: ProductsUiState,
    origin: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SectionHeader("Product details")
        Text("Opened from: $origin")
        val product = uiState.selectedProduct
        if (product == null) {
            Text("Item not found.")
        } else {
            Text("Name: ${product.name}")
            Text("Category: ${product.category}")
            Text("Price: ${product.lowestPriceBam} BAM")
            Text("Store: ${product.storeName}")
            Text("City: ${product.city}")
        }
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
