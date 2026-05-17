package com.example.pricecomparisonapp.model.repository

import com.example.pricecomparisonapp.model.data.local.dao.CategoryDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override fun observeCategoryNames(): Flow<List<String>> {
        return categoryDao.observeCategories().map { categories -> categories.map { it.name } }
    }
}
