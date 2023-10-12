package com.example.mycloset.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen(){
    object SignupScreen: Screen()
    object LoginScreen: Screen()
    object HomeScreen : Screen()
    object ProductScanView : Screen()
    object SingleItemScreen : Screen()
}

object LoginAppRouter{
    val currentScreen: MutableState<Screen> = mutableStateOf(Screen.SignupScreen)

    fun navigateTo(destination: Screen){
        currentScreen.value = destination
    }
}

