package com.example.pricecomparisonapp.model.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FiltersRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _selectedCity = MutableStateFlow(
        prefs.getString(KEY_CITY, DEFAULT_CITY) ?: DEFAULT_CITY
    )
    val selectedCity: StateFlow<String> = _selectedCity.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun setCity(city: String) {
        _selectedCity.value = city
        prefs.edit().putString(KEY_CITY, city).apply()
    }

    fun setCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private companion object {
        const val PREFS_NAME = "app_filters"
        const val KEY_CITY = "selected_city"
        const val DEFAULT_CITY = "Sarajevo"
    }
}
