package com.example.pricecomparisonapp.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pricecomparisonapp.data.model.Product
import com.example.pricecomparisonapp.ui.screens.details.DetailsScreen
import com.example.pricecomparisonapp.ui.screens.form.FormScreen
import com.example.pricecomparisonapp.ui.screens.home.HomeScreen
import com.example.pricecomparisonapp.ui.screens.list.ListScreen
import com.example.pricecomparisonapp.ui.state.AppUiState

private val previewProducts = listOf(
    Product(1, "Milk 1L", "Dairy", 2.30, "Bingo", "Sarajevo"),
    Product(2, "Bread", "Bakery", 1.70, "Konzum", "Sarajevo")
)

@Composable
private fun PreviewContainer(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}

@Preview(showBackground = true, name = "Home")
@Composable
private fun HomeScreenPreview() {
    PreviewContainer {
        HomeScreen(
            uiState = AppUiState(products = previewProducts, selectedCity = "Sarajevo"),
            onSelectCity = {},
            onGoToList = {}
        )
    }
}

@Preview(showBackground = true, name = "List")
@Composable
private fun ListScreenPreview() {
    PreviewContainer {
        ListScreen(
            uiState = AppUiState(products = previewProducts, selectedCity = "Sarajevo"),
            onSelectProduct = {},
            onToggleFavorite = {}
        )
    }
}

@Preview(showBackground = true, name = "Details")
@Composable
private fun DetailsScreenPreview() {
    PreviewContainer {
        DetailsScreen(
            uiState = AppUiState(
                products = previewProducts,
                selectedProduct = previewProducts.first()
            )
        )
    }
}

@Preview(showBackground = true, name = "Form Validation")
@Composable
private fun FormScreenPreview() {
    PreviewContainer {
        FormScreen(
            uiState = AppUiState(
                nameInput = "",
                priceInput = "0",
                categoryInput = "",
                storeInput = "",
                formSubmitted = true
            ),
            onNameChange = {},
            onPriceChange = {},
            onCategoryChange = {},
            onStoreChange = {},
            onSubmit = {}
        )
    }
}
