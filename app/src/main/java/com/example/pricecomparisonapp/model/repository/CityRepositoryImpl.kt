package com.example.pricecomparisonapp.model.repository

import com.example.pricecomparisonapp.model.data.local.dao.CityDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityRepositoryImpl @Inject constructor(
    private val cityDao: CityDao
) : CityRepository {
    override fun observeCityNames(): Flow<List<String>> {
        return cityDao.observeCities().map { cities -> cities.map { it.name } }
    }
}
