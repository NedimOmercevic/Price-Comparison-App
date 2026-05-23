package com.example.pricecomparisonapp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pricecomparisonapp.presentation.navigation.AppRoutes
import com.example.pricecomparisonapp.presentation.theme.AppTheme
import com.example.pricecomparisonapp.presentation.ui.components.AppBottomBar
import com.example.pricecomparisonapp.presentation.ui.screens.AddProductScreen
import com.example.pricecomparisonapp.presentation.ui.screens.DetailsScreen
import com.example.pricecomparisonapp.presentation.ui.screens.FavoritesScreen
import com.example.pricecomparisonapp.presentation.ui.screens.HomeScreen
import com.example.pricecomparisonapp.presentation.ui.screens.ProductsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceComparisonApp() {
    AppTheme {
        val navController = rememberNavController()
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        Scaffold(
            topBar = { TopAppBar(title = { Text("Price Comparison BiH") }) },
            bottomBar = {
                AppBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(AppRoutes.Home.route) { saveState = true }
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AppRoutes.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(AppRoutes.Home.route) {
                    HomeScreen()
                }
                composable(AppRoutes.Products.route) {
                    ProductsScreen(
                        onProductClick = { productId ->
                            navController.navigate(
                                AppRoutes.Details.createRoute(productId.toInt(), "products")
                            )
                        }
                    )
                }
                composable(AppRoutes.Favorites.route) {
                    FavoritesScreen(
                        onOpenDetails = { productId ->
                            navController.navigate(
                                AppRoutes.Details.createRoute(productId.toInt(), "favorites")
                            )
                        }
                    )
                }
                composable(AppRoutes.AddProduct.route) {
                    AddProductScreen(
                        onSaved = {
                            navController.navigate(AppRoutes.Products.route) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
                composable(
                    route = AppRoutes.Details.route,
                    arguments = listOf(
                        navArgument("productId") { type = NavType.IntType },
                        navArgument("origin") { type = NavType.StringType }
                    )
                ) {
                    DetailsScreen(onBack = { navController.popBackStack() })
                }
            }
        }
    }
}
