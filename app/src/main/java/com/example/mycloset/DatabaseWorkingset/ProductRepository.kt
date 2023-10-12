package com.example.mycloset.DatabaseWorkingset

import kotlinx.coroutines.flow.Flow

// Repository that provides insert, update, delete, and retrieve of [Product] from a given data source.
interface ProductRepository {
    fun getAllProductsWithEmailStream(userEmail: String): Flow<List<ProductEntity>>
    suspend fun updateProductDetailsStream(product: ProductEntity)
    suspend fun deleteProductStream(product: ProductEntity)
    fun getSingleProductStream(barcode:String,userEmail: String):ProductEntity
}