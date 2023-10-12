package com.example.mycloset.App

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.mycloset.Views.HomeScreenView
import com.example.mycloset.Views.LoginScreen
import com.example.mycloset.Views.SignUpScreen
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen

@Composable
fun LoginApp(){
    Surface(modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Crossfade(targetState = LoginAppRouter.currentScreen, label = "") { currentState ->
            when(currentState.value){
                is Screen.SignupScreen ->{
                    SignUpScreen()
                }
                is Screen.LoginScreen ->{
                    LoginScreen()
                }
                is Screen.HomeScreen ->{
                    HomeScreenView()
                }
            }
            
        }
    }
}