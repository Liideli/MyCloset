package com.example.mycloset.App

import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycloset.AppViewModelProvider
import com.example.mycloset.BarcodeWorkingSet.CameraScanViewModel
import com.example.mycloset.DatabaseWorkingset.ProductViewModel
import com.example.mycloset.Views.HomeScreen
import com.example.mycloset.Views.LoginScreen
import com.example.mycloset.Views.MainScreen
import com.example.mycloset.Views.ProductScanView
import com.example.mycloset.Views.SignUpScreen
import com.example.mycloset.Views.SingleItemScreen
import com.example.mycloset.Views.UpdateSingleItem
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen


@Composable
fun LoginApp(cameraController: LifecycleCameraController){
    val productViewModel: ProductViewModel = viewModel(factory = AppViewModelProvider.Factory)
    // ViewModel for handling camera and barcode scanning logic
    val viewModel: CameraScanViewModel = viewModel(factory = AppViewModelProvider.Factory)

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
                    HomeScreen(productViewModel)
                }
                is Screen.ProductScanView ->{
                    ProductScanView(
                        productViewModel,
                        barcodesFlow = viewModel.barcodesFlow,
                        cameraController,
                        torchEnabledFlow = viewModel.torchFlow,
                        onTorchButtonClicked = {
                            // Toggle torch status when the button is clicked
                            cameraController.enableTorch(!viewModel.torchFlow.value)
                            viewModel.updateTorchEnabled()
                        }
                    )
                }
                is Screen.MainScreen -> {
                    MainScreen()
                }


                is Screen.SingleItemScreen ->{
                    SingleItemScreen(productViewModel)
                }
                is Screen.UpdateSingleScreen->{
                    UpdateSingleItem(productViewModel)
                }
            }
            
        }
    }
}

