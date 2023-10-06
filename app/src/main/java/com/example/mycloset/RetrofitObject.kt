package com.example.mycloset

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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