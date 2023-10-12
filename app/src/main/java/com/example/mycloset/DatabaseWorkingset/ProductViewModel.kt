package com.example.mycloset.DatabaseWorkingset


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycloset.ApiWorkingSet.RetrofitObject
import com.example.mycloset.BarcodeWorkingSet.BarcodeRepository
import com.example.mycloset.LoginWorkingSet.LoggedUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//view-model used for the take the information about one product from its barcode
class ProductViewModel(val productDao: ProductDao, val productRepository: ProductRepository) :
    ViewModel() {
    private val repository: BarcodeRepository = BarcodeRepository()
    var informationProductObject by mutableStateOf(ProductObject("","","","","", "", "","","",""))
    var products by mutableStateOf(emptyList<ProductEntity>())


    var selectedProduct by mutableStateOf(String())
    fun setSelectedProduct(product: String): String {
        selectedProduct = product
        return selectedProduct
    }

    fun saveToDatabase(
        obj:ProductObject
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val productInformation = ProductEntity(obj.barcodeNumber,obj.userEmail,obj.model,obj.title,obj.category,obj.brand,obj.color,obj.material,obj.size,obj.images)
            productDao.insertProduct(productInformation)
            Log.i("SAV", "Saved to database")
        }
    }

    // Get all the products from an account
    fun getProductsWithEmail(userEmail: String) {
        viewModelScope.launch {
            productRepository.getAllProductsWithEmailStream(userEmail).collect() { response ->
                if (response != null) {
                    products = response
                }
            }
        }
    }

    fun getProductWithBarcode(barcodeNumber: String) {
        viewModelScope.launch {
            productRepository.getAllProductWithBarcodeStream(barcodeNumber).collect() { response ->
                products = response
            }
        }
    }


    //update
    fun updateProductDetails(product:ProductEntity){
        viewModelScope.launch {
            productRepository.updateProductDetailsStream(product)
            }
        }

    //delete
    fun deleteProduct(obj:ProductObject){
        viewModelScope.launch {
            productRepository.deleteProductStream(ProductEntity(obj.barcodeNumber,obj.userEmail,obj.model,obj.title,obj.category,obj.brand,obj.color,obj.material,obj.size,obj.images)
            )
        }
    }

    //the result will be an array of strings
    fun getInfo(barcode: String) {
        // Code for API call and conversion
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val serverResp = repository.takeInformation(barcode)
                val result = convertFromApi(serverResp.products)
                informationProductObject = result
            } catch (e: Exception) {
                Log.e("Error", "Exception: ${e.message}")
            }

        }
    }
}

data class ProductObject(
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

//Function for convert from the Api result to a map with all the information about a product
fun convertFromApi(products: List<RetrofitObject.ModelResult.Product>): ProductObject {
    return ProductObject(
        products[0].barcode_number,
        LoggedUser.loggedUserEmail,
        products[0].model,
        products[0].title,
        products[0].category,
        products[0].brand,
        products[0].color,
        products[0].material,
        products[0].size,
        products[0].images[0]
    )
}