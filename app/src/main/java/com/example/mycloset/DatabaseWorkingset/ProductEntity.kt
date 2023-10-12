package com.example.mycloset.DatabaseWorkingset

import androidx.room.Entity

@Entity(tableName = "products",primaryKeys = ["barcodeNumber", "userEmail"])
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
