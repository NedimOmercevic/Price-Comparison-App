package com.example.pricecomparisonapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun FilterChipRow(
    items: List<String>,
    selected: String?,
    onSelect: (String?) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        item {
            AssistChip(
                onClick = { onSelect(null) },
                label = { Text("All") }
            )
        }
        items(items) { item ->
            AssistChip(
                onClick = { onSelect(item) },
                label = {
                    Text(if (selected == item) "$item ✓" else item)
                }
            )
        }
    }
}
