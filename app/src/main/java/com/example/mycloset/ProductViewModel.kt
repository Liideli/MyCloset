package com.example.mycloset


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//view-model used for the take the information about one product from its barcode
class ProductViewModel(val productDao: ProductDao, val productRepository: ProductRepository) :
    ViewModel() {
    private val repository: BarcodeRepository = BarcodeRepository()
    var informationProductMap by mutableStateOf(emptyMap<String, String>())
    var products by mutableStateOf(emptyList<ProductEntity>())

    fun saveToDatabase(
        barcodeNumber: String,
        userEmail: String,
        model: String,
        title: String,
        category: String,
        brand: String,
        color: String,
        material: String,
        size: String,
        images: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val productInformation = ProductEntity(
                barcodeNumber,
                userEmail,
                model,
                title,
                category,
                brand,
                color,
                material,
                size,
                images
            )
            productDao.insertProduct(productInformation)
            Log.i("SAV", "Saved to database")
        }
    }

    // Get all products
    fun getProducts() {
        viewModelScope.launch {
            productRepository.getAllProductsStream().collect() { response ->
                products = response
            }
        }
    }

    // Get products with logged in email
    fun getProductsWithEmail(userEmail: String) {
        viewModelScope.launch {
            productRepository.getAllProductsWithEmailStream(userEmail).collect() { response ->
                products = response
            }
        }
    }

    //the result will be an array of strings
    fun getInfo(barcode: String) {
        // Code for API call and conversion
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val serverResp = repository.takeInformation(barcode)
                val result = convertFromApi(serverResp.products)
                informationProductMap = result
            } catch (e: Exception) {
                Log.e("Error", "Exception: ${e.message}")
            }

        }
    }
}

//Function for convert from the Api result to a map with all the information about a product
fun convertFromApi(products: List<RetrofitObject.ModelResult.Product>): Map<String, String> {
    val result = mutableMapOf<String, String>()
    //save the data from the call in the new Map
    result["barcodeNumber"] = products[0].barcode_number
    result["model"] = products[0].model
    result["title"] = products[0].title
    result["category"] = products[0].category
    result["brand"] = products[0].brand
    result["color"] = products[0].color
    result["material"] = products[0].material
    result["size"] = products[0].size
    result["images"] = products[0].images[0]
    return result
}