package com.example.mycloset.DatabaseWorkingset

import androidx.room.Query
import kotlinx.coroutines.flow.Flow

class OfflineProductRepository(private val productDao: ProductDao) : ProductRepository {
    override fun getAllProductsWithEmailStream(userEmail: String) = productDao.getProductsWithEmail(userEmail)

    override fun getAllProductWithBarcodeStream(barcodeNumber: String) = productDao.getProductWithBarcode(barcodeNumber)

    override suspend fun updateProductDetailsStream(product: ProductEntity) = productDao.updateProductDetails(product)

    override suspend fun deleteProductStream(barcodeNumber: String) = productDao.deleteProduct(barcodeNumber)

    //override fun getSingleProductStream(barcode:String,userEmail: String)=productDao.getSingleProduct(barcode,userEmail)
}