package com.example.mycloset.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

// Define a sealed class named Screen to represent different screens or destinations in the app.
sealed class Screen {
    // Object representing the signup screen.
    object SignupScreen: Screen()

    // Object representing the login screen.
    object LoginScreen: Screen()

    // Object representing the home screen.
    object HomeScreen: Screen()

    // Object representing the product scanning view screen.
    object ProductScanView: Screen()

    // Object representing the main screen.
    object MainScreen: Screen()

    // Object representing the single item screen.
    object SingleItemScreen: Screen()

    // Object representing the update single item screen.
    object UpdateSingleScreen: Screen()
}

// Define an object named LoginAppRouter to manage app navigation.
object LoginAppRouter {
    // Create a mutable state variable to track the current screen.
    val currentScreen: MutableState<Screen> = mutableStateOf(Screen.MainScreen)

    // Function to navigate to a specified destination screen.
    fun navigateTo(destination: Screen) {
        currentScreen.value = destination
    }
}


