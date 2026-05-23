package com.example.pricecomparisonapp.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import com.example.pricecomparisonapp.presentation.navigation.AppRoutes

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        Triple(AppRoutes.Home.route, "Home", Icons.Default.Home),
        Triple(AppRoutes.Products.route, "Products", Icons.Default.List),
        Triple(AppRoutes.Favorites.route, "Favs", Icons.Default.Favorite),
        Triple(AppRoutes.AddProduct.route, "Add", Icons.Default.Add)
    )

    NavigationBar {
        items.forEach { (route, label, icon) ->
            NavigationBarItem(
                selected = currentRoute == route,
                onClick = { onNavigate(route) },
                icon = { Icon(imageVector = icon, contentDescription = label) },
                label = {
                    Text(
                        text = label,
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        softWrap = false
                    )
                }
            )
        }
    }
}
