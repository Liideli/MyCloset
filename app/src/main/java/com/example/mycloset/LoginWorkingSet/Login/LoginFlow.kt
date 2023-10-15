package com.example.mycloset.LoginWorkingSet.Login

import android.app.Application
import com.google.firebase.FirebaseApp

// Define a class called LoginFlow that extends the Application class.
class LoginFlow: Application() {
    // Override the onCreate method, which is called when the application starts.
    override fun onCreate() {
        super.onCreate()
        // Initialize FirebaseApp to set up Firebase services and configurations.
        FirebaseApp.initializeApp(this)
    }
}
