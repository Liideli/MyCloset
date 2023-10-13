package com.example.mycloset.DatabaseWorkingset

// This class serves as an implementation of the ProductRepository interface.
class OfflineProductRepository(private val productDao: ProductDao) : ProductRepository {

    // Retrieves a list of products associated with the provided user email.
    override fun getAllProductsWithEmailStream(userEmail: String) = productDao.getProductsWithEmail(userEmail)

    // Retrieves a product associated with the provided barcode number.
    override fun getAllProductWithBarcodeStream(barcodeNumber: String) = productDao.getProductWithBarcode(barcodeNumber)

    // Updates the details of the provided product entity.
    override suspend fun updateProductDetailsStream(product: ProductEntity) = productDao.updateProductDetails(product)

    // Deletes a product associated with the provided barcode number.
    override suspend fun deleteProductStream(barcodeNumber: String) = productDao.deleteProduct(barcodeNumber)
}
