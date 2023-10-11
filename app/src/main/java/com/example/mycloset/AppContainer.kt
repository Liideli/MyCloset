package com.example.mycloset

import android.content.Context
import com.example.mycloset.DatabaseWorkingset.ProductDatabase


/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val appDatabase: ProductDatabase
}

class AppDataContainer(private val context: Context) :
    AppContainer {

    override val appDatabase: ProductDatabase by lazy {
        ProductDatabase.getInstance(context)
    }
}