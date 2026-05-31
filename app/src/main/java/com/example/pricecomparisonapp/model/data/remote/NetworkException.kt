package com.example.pricecomparisonapp.model.data.remote

class NetworkException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)
