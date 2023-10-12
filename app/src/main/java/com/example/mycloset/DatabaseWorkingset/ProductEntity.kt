package com.example.mycloset.DatabaseWorkingset

import androidx.room.Entity

@Entity(tableName = "products",primaryKeys = ["barcodeNumber", "userEmail"])
data class ProductEntity(
    val barcodeNumber: String,
    val userEmail: String,
    val model: String,
    val title: String,
    val category: String,
    val brand: String,
    val color: String,
    val material: String,
    val size: String,
    val images: String
)
