package com.example.mycloset.BarcodeWorkingSet

import com.example.mycloset.ApiWorkingSet.RetrofitObject

//
var APY_KEY:String="ru4nshybnq1qak5ery892a1fdnw330"

//class for using the barcode-lookup API
class BarcodeRepository{
    //used for saving the data received with the JSON
    //If we finish the request for the firs account, we have to create a new one
    //if you change key="" you change the account used for the call
    suspend fun takeInformation(barcode: String) = RetrofitObject.service.productInformation(barcode,
        APY_KEY
    )
}