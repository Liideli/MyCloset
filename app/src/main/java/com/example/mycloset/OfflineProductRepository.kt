package com.example.mycloset

class OfflineProductRepository(private val productDao: ProductDao) : ProductRepository {
    override fun getAllProductsStream() = productDao.getAllProducts()

    override fun getAllProductsWithEmailStream(userEmail: String) = productDao.getProductsWithEmail(userEmail)

}