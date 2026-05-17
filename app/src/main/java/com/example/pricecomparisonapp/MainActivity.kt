package com.example.pricecomparisonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.pricecomparisonapp.model.data.local.util.AppDatabaseInitializer
import com.example.pricecomparisonapp.presentation.PriceComparisonApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var databaseInitializer: AppDatabaseInitializer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            databaseInitializer.ensureReady()
        }
        setContent {
            PriceComparisonApp()
        }
    }
}
