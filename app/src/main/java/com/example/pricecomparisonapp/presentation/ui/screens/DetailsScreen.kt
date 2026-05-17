package com.example.pricecomparisonapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pricecomparisonapp.presentation.common.ScreenUiState
import com.example.pricecomparisonapp.presentation.ui.components.SectionHeader
import com.example.pricecomparisonapp.presentation.viewmodel.details.DetailsSuccessData
import com.example.pricecomparisonapp.presentation.viewmodel.details.DetailsViewModel

@Composable
fun DetailsScreen(
    onBack: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DetailsScreenStateless(
        uiState = uiState,
        onBack = onBack,
        onToggleFavorite = viewModel::onToggleFavorite,
        onDelete = {
            viewModel.onDeleteProduct()
            onBack()
        }
    )
}

@Composable
fun DetailsScreenStateless(
    uiState: ScreenUiState<DetailsSuccessData>,
    onBack: () -> Unit,
    onToggleFavorite: () -> Unit,
    onDelete: () -> Unit
) {
    when (uiState) {
        ScreenUiState.Init, ScreenUiState.Loading -> CircularProgressIndicator(Modifier.padding(16.dp))
        is ScreenUiState.Error -> Text("Error: ${uiState.message}", Modifier.padding(16.dp))
        is ScreenUiState.Success -> {
            val product = uiState.data.product
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SectionHeader("Product details")
                Text("Opened from: ${uiState.data.origin}")
                Text("Name: ${product.name}")
                Text("Category: ${product.categoryName}")
                Text("Price: ${product.priceBam} BAM")
                Text("Store: ${product.storeName}")
                Text("City: ${product.cityName}")
                Button(onClick = onToggleFavorite) { Text("Toggle favorite") }
                Button(onClick = onDelete) { Text("Delete product") }
                Button(onClick = onBack) { Text("Back") }
            }
        }
    }
}
