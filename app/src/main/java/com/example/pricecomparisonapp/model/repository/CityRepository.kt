package com.example.pricecomparisonapp.model.repository

import kotlinx.coroutines.flow.Flow

interface CityRepository {
    fun observeCityNames(): Flow<List<String>>
}
