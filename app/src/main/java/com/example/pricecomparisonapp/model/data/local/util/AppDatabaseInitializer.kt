package com.example.pricecomparisonapp.model.data.local.util

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDatabaseInitializer @Inject constructor(
    private val databaseSeeder: DatabaseSeeder
) {
    private val mutex = Mutex()
    private var initialized = false

    suspend fun ensureReady() {
        mutex.withLock {
            if (initialized) return
            databaseSeeder.seedIfEmpty()
            initialized = true
        }
    }
}
