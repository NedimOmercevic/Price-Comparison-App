package com.example.pricecomparisonapp.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.pricecomparisonapp.presentation.viewmodel.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceComparisonApp(viewModel: ProductsViewModel = viewModel()) {
    AppTheme {
        val navController = rememberNavController()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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
                modifier = Modifier
                    .padding(innerPadding)
                    .testTag("root_nav_host")
            ) {
                composable(AppRoutes.Home.route) {
                    HomeScreen(
                        uiState = uiState,
                        onSearchChange = viewModel::updateSearchQuery,
                        onCitySelect = viewModel::setCity
                    )
                }
                composable(AppRoutes.Products.route) {
                    ProductsScreen(
                        uiState = uiState,
                        onCategorySelect = viewModel::setCategory,
                        onProductClick = { productId ->
                            viewModel.selectProduct(productId)
                            navController.navigate(AppRoutes.Details.createRoute(productId, "products"))
                        },
                        onToggleFavorite = viewModel::toggleFavorite
                    )
                }
                composable(AppRoutes.Favorites.route) {
                    FavoritesScreen(
                        uiState = uiState,
                        onCitySelect = viewModel::setCity,
                        onOpenDetails = { productId ->
                            viewModel.selectProduct(productId)
                            navController.navigate(AppRoutes.Details.createRoute(productId, "favorites"))
                        },
                        onToggleFavorite = viewModel::toggleFavorite
                    )
                }
                composable(AppRoutes.AddProduct.route) {
                    AddProductScreen(
                        uiState = uiState,
                        onNameChange = viewModel::updateNameInput,
                        onCategoryChange = viewModel::updateCategoryInput,
                        onStoreChange = viewModel::updateStoreInput,
                        onPriceChange = viewModel::updatePriceInput,
                        onSubmit = {
                            if (viewModel.submitProduct()) {
                                navController.navigate(AppRoutes.Products.route)
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
                ) { entry ->
                    val productId = entry.arguments?.getInt("productId") ?: -1
                    val origin = entry.arguments?.getString("origin").orEmpty()
                    if (uiState.selectedProductId != productId) {
                        viewModel.selectProduct(productId)
                    }
                    DetailsScreen(
                        uiState = uiState,
                        origin = origin,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
