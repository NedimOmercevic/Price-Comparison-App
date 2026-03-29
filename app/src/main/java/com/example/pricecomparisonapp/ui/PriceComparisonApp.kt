package com.example.pricecomparisonapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pricecomparisonapp.ui.navigation.AppDestination
import com.example.pricecomparisonapp.ui.screens.details.DetailsScreen
import com.example.pricecomparisonapp.ui.screens.favorites.FavoritesScreen
import com.example.pricecomparisonapp.ui.screens.form.FormScreen
import com.example.pricecomparisonapp.ui.screens.home.HomeScreen
import com.example.pricecomparisonapp.ui.screens.list.ListScreen
import com.example.pricecomparisonapp.ui.viewmodel.AppViewModel

@Composable
fun PriceComparisonApp(viewModel: AppViewModel = viewModel()) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AppDestination.entries.forEach { destination ->
                Button(
                    onClick = { navController.navigate(destination.route) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = destination.title,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }

        NavHost(
            navController = navController,
            startDestination = AppDestination.Home.route
        ) {
            composable(AppDestination.Home.route) {
                HomeScreen(
                    uiState = uiState,
                    onSelectCity = viewModel::selectCity,
                    onGoToList = { navController.navigate(AppDestination.List.route) }
                )
            }
            composable(AppDestination.List.route) {
                ListScreen(
                    uiState = uiState,
                    onSelectProduct = {
                        viewModel.selectProduct(it)
                        navController.navigate(AppDestination.Details.route)
                    },
                    onToggleFavorite = viewModel::toggleFavorite
                )
            }
            composable(AppDestination.Details.route) {
                DetailsScreen(uiState = uiState)
            }
            composable(AppDestination.Form.route) {
                FormScreen(
                    uiState = uiState,
                    onNameChange = viewModel::updateNameInput,
                    onPriceChange = viewModel::updatePriceInput,
                    onCategoryChange = viewModel::updateCategoryInput,
                    onStoreChange = viewModel::updateStoreInput,
                    onSubmit = viewModel::submitProduct
                )
            }
            composable(AppDestination.Favorites.route) {
                FavoritesScreen(
                    uiState = uiState,
                    onToggleFavorite = viewModel::toggleFavorite
                )
            }
        }
    }
}
