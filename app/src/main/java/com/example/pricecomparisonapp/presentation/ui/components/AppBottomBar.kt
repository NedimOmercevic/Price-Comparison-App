package com.example.pricecomparisonapp.presentation.ui.components

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.pricecomparisonapp.presentation.navigation.AppRoutes

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        AppRoutes.Home.route to "Home",
        AppRoutes.Products.route to "Products",
        AppRoutes.Favorites.route to "Favorites",
        AppRoutes.AddProduct.route to "Add"
    )

    NavigationBar {
        items.forEach { (route, label) ->
            NavigationBarItem(
                selected = currentRoute == route,
                onClick = { onNavigate(route) },
                label = { Text(label) },
                icon = {}
            )
        }
    }
}
