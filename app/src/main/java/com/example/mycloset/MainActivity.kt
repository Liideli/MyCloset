package com.example.mycloset

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycloset.ui.theme.MyClosetTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val productViewModel by viewModels<ProductViewModel>()
        super.onCreate(savedInstanceState)
        setContent {
            MyClosetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val product="883412740906"
                    ProductInfoScreen(productViewModel)
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
    model.getInfo(barcodeAndKey)
    result=model.informationProductArray
    return result
}

@Composable
fun ProductInfoScreen(productViewModel: ProductViewModel) {
    val informationProductArray by rememberUpdatedState(newValue = productViewModel.informationProductArray)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = { productViewModel.getInfo("883412740906") }) {
            Text("Fetch Product Info")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider(modifier = Modifier.padding(vertical = 20.dp))

        LazyColumn {
            items(informationProductArray.entries.toList()) { entry ->
                Text("${entry.key} : ${entry.value};")
            }
        }
    }
}



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
                Log.i("WORKS", "The information are successfully saved---> ${result["barcode"]}")
                informationProductArray = result
            }
    }




    /*fun getInfo(barcode: String):Map<String,String>{
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
    }*/
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

//class for using the barcode-lookup API
class BarcodeRepository{
    //used for saving the data received with the JSON

    //If we finish the request for the firs account, we have to create a new one
    //if you change key="" you change the account used for the call
    suspend fun takeInformation(barcode: String) = RetrofitObject.service.productInformation(barcode,key="f25572r7skbz3d153rlw0ockqhcflp")
}


//Object that contain all the component for using retrofit
object RetrofitObject{

    //I use this for map all the field of the JSON result from the API
    object ModelResult{
        data class Model(
            val products: List<Product>
        )
        //List of the field
        data class Product(
            val barcode_number: String,
            val model: String,
            val title: String,
            val category: String,
            val brand: String,
            val age_group: String,
            val color: String,
            val gender: String,
            val material: String,
            val pattern: String,
            val size: String,
            val description: String,
            val images: List<String>
        )
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
            @Query("key")key:String
        ):ModelResult.Model
    }

    //here is create the mew retrofit service
    val service: Service by lazy {
        retrofit.create(Service::class.java)
    }

}




















