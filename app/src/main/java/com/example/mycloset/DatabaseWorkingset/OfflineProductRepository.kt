package com.example.mycloset.DatabaseWorkingset

import androidx.room.Query
import kotlinx.coroutines.flow.Flow

class OfflineProductRepository(private val productDao: ProductDao) : ProductRepository {
    override fun getAllProductsWithEmailStream(userEmail: String) = productDao.getProductsWithEmail(userEmail)

    override fun getAllProductWithBarcodeStream(barcodeNumber: String) = productDao.getProductWithBarcode(barcodeNumber)

    // override fun getAllProductWithBarcodeStream(barcodeNumber: String ,userEmail: String)=productDao.getProductWithBarcode(barcodeNumber, userEmail)
    override suspend fun updateProductDetailsStream(product: ProductEntity) =productDao.updateProductDetails(product)

    //override suspend fun deleteProductStream(product: ProductEntity) =productDao.deleteProduct(product)

    override fun getSingleProductStream(barcode:String,userEmail: String)=productDao.getSingleProduct(barcode,userEmail)
}