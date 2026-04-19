package com.example.pricecomparisonapp.presentation.navigation

sealed class AppRoutes(val route: String) {
    data object Home : AppRoutes("home")
    data object Products : AppRoutes("products")
    data object Favorites : AppRoutes("favorites")
    data object AddProduct : AppRoutes("addProduct")
    data object Details : AppRoutes("details/{productId}/{origin}") {
        fun createRoute(productId: Int, origin: String): String {
            return "details/$productId/$origin"
        }
    }
}
