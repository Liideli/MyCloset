package com.example.mycloset

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycloset.ui.theme.MyClosetTheme
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyClosetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val product="883412740906"
                    PrintResult(product)
                }
            }
        }
    }
}


//Function for print the result of the API call
@Composable
fun PrintResult(req: String){
    var informationProductArray:Map<String, String> =emptyMap()
    Column {
        Button(onClick = {informationProductArray= apiRequest(req) }) {

        }
        Divider(
            modifier = Modifier.padding(vertical = 20.dp)
        )
        LazyColumn {
            items(informationProductArray.entries.toList()) { entry ->
                Text("${entry.key} : ${entry.value};")
            }
        }
    }
}

//this function receive thee barcode and give back the map with all teh information
fun apiRequest(barcodeAndKey:String):Map<String,String>{
    val result:Map<String,String>
    val model=ProductViewModel()
    result=model.getInfo(barcodeAndKey)
    return result
}


//view-model used for the take the information about one product from its barcode
class ProductViewModel : ViewModel() {
    private val repository: BarcodeRepository=BarcodeRepository()
    private var informationProductArray: Map<String,String> by mutableStateOf(emptyMap())

    //the result will be an array of strings
    fun getInfo(barcode: String):Map<String,String>{
        val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
            Log.d("coroutineExceptionHandler", "yes this happened")
            throwable.printStackTrace()
        }
        runBlocking{

            val executeOperations = viewModelScope.launch(Dispatchers.IO+coroutineExceptionHandler) {
                val serverResp = repository.takeInformation(barcode)
                val result = convertFromApi(serverResp.products)
                //Log.i("WORKS", "The information are successfully saved---> ${result["barcode"]}")
                informationProductArray = result
            }
            executeOperations.join()
        }
        return informationProductArray
    }
}


//Function for convert from the Api result to a map with all the information about a product
fun convertFromApi(products: List<RetrofitObject.ModelResult.ProductsData>):Map<String,String>{
    val result=mutableMapOf<String, String>()
    //save the data from the call in the new Map
    result["barcodeNumber"]=products[0].barcodeNumber
    result["model"]=products[0].model
    result["title"]=products[0].title
    result["category"]=products[0].category
    result["brand"]=products[0].brand
    result["ageGroup"]=products[0].ageGroup
    result["color"]=products[0].color
    result["gender"]=products[0].gender
    result["material"]=products[0].material
    result["pattern"]=products[0].pattern
    result["size"]=products[0].size
    result["description"]=products[0].description
    result["images"]= products[0].images[0]
    return result
}

//class for using the barcode-lookup API
class BarcodeRepository{
    //used for saving the data received with the JSON

    //If we finish the request for the firs account, we have to create a new one
    //if you change key="" you change the account used for the call
    suspend fun takeInformation(barcode: String) = RetrofitObject.service.productInformation(barcode,key="r397xoebc6u5t57f8sr9gw7atxyqjl")
}


//Object that contain all the component for using retrofit
object RetrofitObject{

    //I use this for map all the field of the JSON result from the API
    object ModelResult{
        data class ProductResponse(
            var products: List<ProductsData>
        )
        //List of the field
        data class ProductsData(
            val barcodeNumber: String,
            val model: String,
            val title: String,
            val category: String,
            val brand: String,
            val ageGroup: String,
            val color: String,
            val gender: String,
            val material: String,
            val pattern: String,
            val size: String,
            val description: String,
            val images: List<String>
        )

        /* I DON'T THINK WE NEED IT BUT...
           "features": [
           "Air-Sole unit provides added shock absorption.",
           "Genuine leather upper.",
           "Full-length Phylon midsole.",
           "Padded ankle collar provides a comfortable fit.",
           "Solid rubber outsole supplies durable traction."
           ]
       */
    }

    //declaration
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.barcodelookup.com/")
        //used for manage and configure the connections (also things like timeout)
        .client(OkHttpClient.Builder().build())
        //allow the JSON conversion
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //Interface for retrofit
    interface Service {
        //I'm gonna add the other parameters to teh api call
        @GET("v3/products")

        //after this will be added the barcode of the product + the key from the account for make the request
        suspend fun productInformation(
            @Query("barcode")barcode:String,
            @Query("formatted")formatted:String="y",
            @Query("key")key:String
        ):ModelResult.ProductResponse
    }

    //here is create the mew retrofit service
    val service: Service by lazy {
        retrofit.create(Service::class.java)
    }

}




















