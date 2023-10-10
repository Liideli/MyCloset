package com.example.mycloset.Views

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mycloset.BarcodeModel
import com.example.mycloset.ImgDisplay
import com.example.mycloset.ProductViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ProductScanView(
    // Parameters required for the composable
    productViewModel: ProductViewModel,    // ViewModel for managing product information
    barcodesFlow: StateFlow<BarcodeModel>, // StateFlow for real-time barcode information
    cameraController: LifecycleCameraController, // Controller for camera preview
    torchEnabledFlow: StateFlow<Boolean>,  // StateFlow for torch/flashlight status
    onTorchButtonClicked: () -> Unit      // Lambda function for torch button click event
) {
    // State for managing informationProductMap
    val informationProductMap by rememberUpdatedState(newValue = productViewModel.informationProductMap)

    // State for managing whether to show product information
    var showProductInfo by remember { mutableStateOf(false) }

    // State for managing barcode information
    val barcode: State<BarcodeModel> = barcodesFlow.collectAsState()

    // State for managing loading state
    var isLoading by remember { mutableStateOf(false) }

    // State for managing network result
    var networkResult by remember { mutableStateOf<Result<String>?>(null) }

    // State for managing torch enabled/disabled
    val torchEnabled: State<Boolean> = torchEnabledFlow.collectAsState()

    val context = LocalContext.current

    val barcodeNumber: String = informationProductMap["barcodeNumber"].toString()
    val model: String = informationProductMap["model"].toString()
    val title: String = informationProductMap["title"].toString()
    val category: String = informationProductMap["category"].toString()
    val brand: String = informationProductMap["brand"].toString()
    val color: String = informationProductMap["color"].toString()
    val material: String = informationProductMap["material"].toString()
    val size: String = informationProductMap["size"].toString()
    val images: String = informationProductMap["images"].toString()



    if (!showProductInfo) {
        // AndroidView to display the camera preview
        AndroidView(factory = { context ->
            PreviewView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                scaleType = PreviewView.ScaleType.FILL_START
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                this.controller = cameraController
            }
        })

        // Column to organize UI elements vertically with space between them
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Card composable to display barcode information
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        if (barcode.value.barcode.isNotEmpty()) {
                            Text(text = "Barcode: ${barcode.value.barcode}")
                        } else {
                            Text(text = "Point camera at a barcode")
                        }
                    }
                }
                // Row for torch button and search button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    // Button to toggle the torch
                    Button(
                        onClick = onTorchButtonClicked,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        if (torchEnabled.value) {
                            Icon(
                                imageVector = Icons.Default.FlashOff,
                                contentDescription = "Flash off icon"
                            )

                        } else {
                            Icon(
                                imageVector = Icons.Default.FlashOn,
                                contentDescription = "Flash on icon"
                            )
                        }
                    }

                    // Button to search for product info
                    Button(onClick = {
                        if (barcode.value.barcode.isNotEmpty()) {
                            isLoading = true
                            networkResult = null
                            productViewModel.getInfo(barcode.value.barcode)
                            showProductInfo = true
                        } else {
                            Toast.makeText(context, "No barcode found", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }) {
                        Text(text = "Search Product Info")
                    }
                }
            }
        }
    }

    if (informationProductMap.isEmpty()) {
        // Display a loading indicator while loading product info
        if (isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        }
    }

    // Display the product information if there is a successful result
    if (showProductInfo && informationProductMap.isNotEmpty()) {
        isLoading = false
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = title,
                modifier = Modifier.padding(16.dp)
            )

            // Image of the product
            ImgDisplay.DisplayPicture(informationProductMap["images"].toString())

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
                                Text(text = value)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            // Buttons for cancel and add actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { showProductInfo = false }) {
                    Text(text = "Cancel")
                }
                Button(onClick = {  productViewModel.saveToDatabase(barcodeNumber, model, title, category, brand, color, material, size, images) }) {
                    Text(text = "Add")
                }
            }
        }
    }
}
