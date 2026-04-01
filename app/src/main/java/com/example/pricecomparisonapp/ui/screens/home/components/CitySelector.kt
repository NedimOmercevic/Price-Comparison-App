package com.example.pricecomparisonapp.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CitySelector(selectedCity: String, onSelectCity: (String) -> Unit) {
    val cities = listOf("Sarajevo", "Tuzla", "Mostar", "Banja Luka", "Zenica")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        cities.take(3).forEach { city ->
            FilterChip(
                selected = selectedCity == city,
                onClick = { onSelectCity(city) },
                label = { Text(city) }
            )
        }
    }
}
