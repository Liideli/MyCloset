package com.example.mycloset

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val barcodeNumber: String,
    val model: String,
    val title: String,
    val category: String,
    val brand: String,
    val color: String,
    val material: String,
    val size: String,
    val images: String
)
