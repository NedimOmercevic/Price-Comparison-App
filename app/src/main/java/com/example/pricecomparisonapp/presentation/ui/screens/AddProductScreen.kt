package com.example.pricecomparisonapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.example.pricecomparisonapp.presentation.viewmodel.add.AddProductFormData
import com.example.pricecomparisonapp.presentation.viewmodel.add.AddProductViewModel

@Composable
fun AddProductScreen(
    onSaved: () -> Unit,
    viewModel: AddProductViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AddProductScreenStateless(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onCategoryChange = viewModel::onCategoryChange,
        onStoreChange = viewModel::onStoreChange,
        onPriceChange = viewModel::onPriceChange,
        onSubmit = { viewModel.submit(onSaved) }
    )
}

@Composable
fun AddProductScreenStateless(
    uiState: ScreenUiState<AddProductFormData>,
    onNameChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onStoreChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    when (uiState) {
        ScreenUiState.Init -> CircularProgressIndicator(Modifier.padding(16.dp))
        ScreenUiState.Loading -> CircularProgressIndicator(Modifier.padding(16.dp))
        is ScreenUiState.Error -> Text("Error: ${uiState.message}", Modifier.padding(16.dp))
        is ScreenUiState.Success -> {
            val form = uiState.data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SectionHeader("Add product (${form.selectedCity})")
                OutlinedTextField(value = form.nameInput, onValueChange = onNameChange, label = { Text("Product name") })
                OutlinedTextField(value = form.categoryInput, onValueChange = onCategoryChange, label = { Text("Category") })
                OutlinedTextField(value = form.storeInput, onValueChange = onStoreChange, label = { Text("Store") })
                OutlinedTextField(value = form.priceInput, onValueChange = onPriceChange, label = { Text("Price BAM") })
                Button(onClick = onSubmit, enabled = form.isFormValid) { Text("Save") }
                if (form.formSubmitted && !form.isFormValid) {
                    Text("Invalid input. Fill all fields correctly.")
                }
            }
        }
    }
}
