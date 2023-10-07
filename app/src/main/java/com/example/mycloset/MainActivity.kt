package com.example.mycloset

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.example.mycloset.ImgDisplay.Companion.DisplayPicture
import com.example.mycloset.ui.theme.MyClosetTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.jetbrains.annotations.Async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.view.LifecycleCameraController
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mycloset.Views.MainScreen
import com.example.mycloset.ui.theme.AppTheme
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode


class MainActivity : ComponentActivity() {

    // ViewModel for handling camera and barcode scanning logic
    private val viewModel: CameraScanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val productViewModel by viewModels<ProductViewModel>()
        super.onCreate(savedInstanceState)

        // Check if all required permissions are granted
        if (allPermissionsGranted()) {

            // Build a camera controller for handling camera operations
            val cameraController = buildCameraController()

            // Set the content of the activity to be a CameraView
            setContent {
                AppTheme {
                    // Add Navigation
                    MainScreen()
                    /*CameraView(
                        cameraController = cameraController,
                        barcodesFlow = viewModel.barcodesFlow,
                        torchEnabledFlow = viewModel.torchFlow,
                        onTorchButtonClicked = {
                            // Toggle torch status when the button is clicked
                            cameraController.enableTorch(!viewModel.torchFlow.value)
                            viewModel.updateTorchEnabled()
                        }
                    )*/
                }
            }
        } else {
            // Request necessary permissions if not granted
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
    }

    // Build and configure a camera controller
    private fun buildCameraController(): LifecycleCameraController {
        val cameraController = LifecycleCameraController(baseContext)
        cameraController.bindToLifecycle(this)

        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
        val barcodeScanner = BarcodeScanning.getClient(options)

        // Set up an analyzer for processing camera frames
        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(this),
            MlKitAnalyzer(
                listOf(barcodeScanner),
                COORDINATE_SYSTEM_VIEW_REFERENCED,
                ContextCompat.getMainExecutor(this)
            ) { result: MlKitAnalyzer.Result? ->
                val barcodeResults = result?.getValue(barcodeScanner)
                if (!barcodeResults.isNullOrEmpty()) {
                    // Notify ViewModel about detected barcodes
                    viewModel.barcodesDetected(barcodeResults)
                }
            }
        )

        return cameraController
    }

    // Check if all required permissions are granted
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = mutableListOf(
            android.Manifest.permission.CAMERA
        ).toTypedArray()
    }

    // Handle the result of permission request
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                buildCameraController()
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}

// Product Info Screen Composable
/*
@Composable
fun ProductInfoScreen(productViewModel: ProductViewModel) {
    val informationProductMap by rememberUpdatedState(newValue = productViewModel.informationProductMap)
    var showProductInfo by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = {
            productViewModel.getInfo()
            showProductInfo = true
        })
        {
            Text("Fetch Product Info")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier.padding(vertical = 20.dp))

        if (showProductInfo) {
            if (informationProductMap.isNotEmpty()) {
                Column {

                    //Title
                    val title: String = informationProductMap["title"].toString()
                    Text(
                        text = title,
                        modifier = Modifier.padding(16.dp)
                    )

                    //Image
                    DisplayPicture(informationProductMap["images"].toString())

                    //Table
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        informationProductMap.forEach { (key, value) ->
                            if (key != "title" && key != "images") {
                                item {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = key, fontWeight = FontWeight.Bold)
                                        Text(text = value.toString())
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }

                    //Button
                    Row {
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Cancel")
                        }
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Add")
                        }

                    }
                }
            }
        }
    }
}




