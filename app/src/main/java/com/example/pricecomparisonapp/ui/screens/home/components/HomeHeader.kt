package com.example.pricecomparisonapp.ui.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeHeader() {
    Column {
        Text(
            text = "Price Comparison BiH",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Compare offers across Sarajevo, Tuzla, Mostar and more.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
