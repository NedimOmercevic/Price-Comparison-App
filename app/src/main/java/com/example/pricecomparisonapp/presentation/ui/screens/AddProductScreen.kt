package com.example.pricecomparisonapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pricecomparisonapp.presentation.ui.components.SectionHeader
import com.example.pricecomparisonapp.presentation.viewmodel.ProductsUiState

@Composable
fun AddProductScreen(
    uiState: ProductsUiState,
    onNameChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onStoreChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SectionHeader("Add product offer")
        OutlinedTextField(
            value = uiState.nameInput,
            onValueChange = onNameChange,
            label = { Text("Product name") }
        )
        OutlinedTextField(
            value = uiState.categoryInput,
            onValueChange = onCategoryChange,
            label = { Text("Category") }
        )
        OutlinedTextField(
            value = uiState.storeInput,
            onValueChange = onStoreChange,
            label = { Text("Store") }
        )
        OutlinedTextField(
            value = uiState.priceInput,
            onValueChange = onPriceChange,
            label = { Text("Price BAM") }
        )
        Button(onClick = onSubmit, enabled = uiState.isFormValid) {
            Text("Save")
        }
        if (uiState.formSubmitted && !uiState.isFormValid) {
            Text("Invalid input. Fill all fields correctly.")
        }
    }
}
