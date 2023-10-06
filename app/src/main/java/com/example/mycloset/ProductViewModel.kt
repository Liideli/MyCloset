package com.example.mycloset


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//view-model used for the take the information about one product from its barcode
class ProductViewModel : ViewModel() {
    private val repository: BarcodeRepository=BarcodeRepository()
    var informationProductArray by mutableStateOf(emptyMap<String, String>())

    //the result will be an array of strings
    fun getInfo(barcode: String){
        /*val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
            Log.d("coroutineExceptionHandler", "yes this happened")
            throwable.printStackTrace()
        }*/
        viewModelScope.launch(Dispatchers.IO) {
            val serverResp = repository.takeInformation(barcode)
            val result = convertFromApi(serverResp.products)
            informationProductArray = result
        }
    }
}

//Function for convert from the Api result to a map with all the information about a product
fun convertFromApi(products: List<RetrofitObject.ModelResult.Product>):Map<String,String>{
    val result=mutableMapOf<String, String>()
    //save the data from the call in the new Map
    result["barcodeNumber"]=products[0].barcode_number
    result["model"]=products[0].model
    result["title"]=products[0].title
    result["category"]=products[0].category
    result["brand"]=products[0].brand
    result["ageGroup"]=products[0].age_group
    result["color"]=products[0].color
    result["gender"]=products[0].gender
    result["material"]=products[0].material
    result["pattern"]=products[0].pattern
    result["size"]=products[0].size
    result["description"]=products[0].description
    result["images"]= products[0].images[0]
    return result
}