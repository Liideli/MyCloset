package com.example.mycloset.LoginWorkingSet

import android.app.Application
import com.google.firebase.FirebaseApp

class LoginFlow: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}