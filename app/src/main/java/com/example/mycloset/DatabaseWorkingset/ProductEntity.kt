package com.example.mycloset.DatabaseWorkingset

import androidx.room.Entity

// This is a data class representing a product entity in the database.
@Entity(tableName = "products", primaryKeys = ["barcodeNumber", "userEmail"])
data class ProductEntity(
    val barcodeNumber: String,
    val userEmail: String,
    var model: String,
    var title: String,
    var category: String,
    var brand: String,
    var color: String,
    var material: String,
    var size: String,
    val images: String
)

