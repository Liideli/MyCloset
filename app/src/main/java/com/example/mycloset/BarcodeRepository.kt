package com.example.mycloset

//
var BARCODE:String="883412740906"
var APY_KEY:String="yaroh228hxv1i2zefkvcv7vpqqn3j4"

//class for using the barcode-lookup API
class BarcodeRepository{
    //used for saving the data received with the JSON
    //If we finish the request for the firs account, we have to create a new one
    //if you change key="" you change the account used for the call
    suspend fun takeInformation() = RetrofitObject.service.productInformation(BARCODE,APY_KEY)
}