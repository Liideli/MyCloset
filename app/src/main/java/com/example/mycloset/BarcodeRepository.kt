package com.example.mycloset

//
var BARCODE:String="883412740906"
var APY_KEY:String="f25572r7skbz3d153rlw0ockqhcflp"

//class for using the barcode-lookup API
class BarcodeRepository{
    //used for saving the data received with the JSON
    //If we finish the request for the firs account, we have to create a new one
    //if you change key="" you change the account used for the call
    suspend fun takeInformation() = RetrofitObject.service.productInformation(BARCODE,APY_KEY)
}