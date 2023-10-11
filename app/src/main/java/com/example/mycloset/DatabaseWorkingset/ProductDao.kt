package com.example.mycloset.DatabaseWorkingset

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    // Get all products
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductEntity>>

    // Get products of logged in user
    @Query("SELECT * from products WHERE userEmail = :userEmail")
    fun getProductsWithEmail(userEmail: String): Flow<List<ProductEntity>>

    @Update
    suspend fun updateProductDetails(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)
}