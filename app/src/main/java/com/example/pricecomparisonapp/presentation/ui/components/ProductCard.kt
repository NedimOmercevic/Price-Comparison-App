package com.example.pricecomparisonapp.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pricecomparisonapp.data.model.Product

@Composable
fun ProductCard(
    product: Product,
    onOpen: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onOpen)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(product.name)
                Text("${product.lowestPriceBam} BAM • ${product.storeName}")
                Text(product.category)
            }
            TextButton(onClick = onToggleFavorite) {
                Text(if (product.isFavorite) "Unfav" else "Fav")
            }
        }
    }
}
