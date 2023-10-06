package com.example.mycloset

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
    val informationProductArray by rememberUpdatedState(newValue = productViewModel.informationProductArray)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = { productViewModel.getInfo() }) {
            Text("Fetch Product Info")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier.padding(vertical = 20.dp))
        LazyColumn {
            items(informationProductArray.entries.toList()) { entry ->
                Text("${entry.key} : ${entry.value};")
            }
        }
    }
}*/
