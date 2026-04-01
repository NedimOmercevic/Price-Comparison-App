package com.example.pricecomparisonapp.ui.screens.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pricecomparisonapp.ui.screens.form.components.FormInputField
import com.example.pricecomparisonapp.ui.screens.form.components.ValidationError
import com.example.pricecomparisonapp.ui.state.AppUiState

@Composable
fun FormScreen(
    uiState: AppUiState,
    onNameChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onStoreChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    val showErrors = uiState.formSubmitted

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Add new product offer")
        FormInputField(
            label = "Product name",
            value = uiState.nameInput,
            onValueChange = onNameChange
        )
        if (showErrors && !uiState.isNameValid) {
            ValidationError("Name must contain at least 2 characters.")
        }

        FormInputField(
            label = "Price (BAM)",
            value = uiState.priceInput,
            onValueChange = onPriceChange
        )
        if (showErrors && !uiState.isPriceValid) {
            ValidationError("Price must be a valid number greater than 0.")
        }

        FormInputField(
            label = "Category",
            value = uiState.categoryInput,
            onValueChange = onCategoryChange
        )
        if (showErrors && !uiState.isCategoryValid) {
            ValidationError("Category is required.")
        }

        FormInputField(
            label = "Store name",
            value = uiState.storeInput,
            onValueChange = onStoreChange
        )
        if (showErrors && !uiState.isStoreValid) {
            ValidationError("Store name is required.")
        }

        Button(
            onClick = onSubmit,
            enabled = uiState.isFormValid
        ) {
            Text("Save product")
        }
        if (!uiState.isFormValid) {
            Text("Fill all fields with valid values to enable submit.")
        }
    }
}
