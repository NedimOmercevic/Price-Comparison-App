package com.example.pricecomparisonapp.ui.screens.form.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ValidationError(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colorScheme.error
    )
}
