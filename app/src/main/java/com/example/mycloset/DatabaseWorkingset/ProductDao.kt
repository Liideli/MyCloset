package com.example.mycloset.DatabaseWorkingset

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// Data Access Object (DAO) interface for the ProductEntity class.
@Dao
interface ProductDao {

    // Inserts a product into the database. If there is a conflict, it replaces the existing data.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    // Retrieves all products from the database and emits them as a Flow (asynchronous stream).
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductEntity>>

    // Retrieves products associated with a specific user's email and emits them as a Flow.
    @Query("SELECT * from products WHERE userEmail = :userEmail")
    fun getProductsWithEmail(userEmail: String): Flow<List<ProductEntity>>

    // Retrieves a product with a specific barcode number and emits it as a Flow.
    @Query("SELECT * from products WHERE barcodeNumber=:barcodeNumber")
    fun getProductWithBarcode(barcodeNumber: String): Flow<List<ProductEntity>>

    // Updates the details of a product in the database.
    @Update
    suspend fun updateProductDetails(product: ProductEntity)

    // Deletes a product from the database using its barcode number.
    @Query("DELETE FROM products WHERE barcodeNumber = :barcodeNumber")
    suspend fun deleteProduct(barcodeNumber: String)
}

