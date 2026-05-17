package com.example.pricecomparisonapp.model.repository

import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observeCategoryNames(): Flow<List<String>>
}
