package com.example.mycloset.DatabaseWorkingset

class OfflineProductRepository(private val productDao: ProductDao) : ProductRepository {
    override fun getAllProductsStream() = productDao.getAllProducts()

    override fun getAllProductsWithEmailStream(userEmail: String) = productDao.getProductsWithEmail(userEmail)
    override suspend fun updateProductDetailsStream(product: ProductEntity) = productDao.updateProductDetails(product)
    override suspend fun deleteProductStream(product: ProductEntity) = productDao.deleteProduct(product)

}