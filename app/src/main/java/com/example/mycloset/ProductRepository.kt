package com.example.mycloset

import kotlinx.coroutines.flow.Flow

// Repository that provides insert, update, delete, and retrieve of [Product] from a given data source.
interface ProductRepository {
    fun getAllProductsStream(): Flow<List<ProductEntity>>

    fun getAllProductsWithEmailStream(userEmail: String): Flow<List<ProductEntity>>
}