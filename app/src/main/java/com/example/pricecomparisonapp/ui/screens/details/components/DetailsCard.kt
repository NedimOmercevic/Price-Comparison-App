package com.example.pricecomparisonapp.ui.screens.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pricecomparisonapp.data.model.Product

@Composable
fun DetailsCard(product: Product) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text("Name: ${product.name}")
            Text("Category: ${product.category}")
            Text("Best offer: ${product.lowestPriceBam} BAM at ${product.storeName}")
            Text("City: ${product.city}")
        }
    }
}
