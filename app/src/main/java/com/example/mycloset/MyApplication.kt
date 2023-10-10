package com.example.mycloset
import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


class MyApplication : Application() {
    val TAG = "[MyApplication]"

    companion object {
        private val parentJob = Job()
        private val coroutineScope = CoroutineScope(Dispatchers.Default + parentJob)
    }

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */

    // Container of repositories
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = AppDataContainer(this)

    }

}