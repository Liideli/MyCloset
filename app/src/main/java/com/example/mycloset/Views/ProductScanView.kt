package com.example.mycloset.Views

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycloset.BarcodeModel
import com.example.mycloset.ImgDisplay
import com.example.mycloset.ProductViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ProductScanView(
    productViewModel: ProductViewModel,
    barcodesFlow: StateFlow<BarcodeModel>,
    cameraController: LifecycleCameraController,
    torchEnabledFlow: StateFlow<Boolean>,
    onTorchButtonClicked: () -> Unit
) {
    val informationProductMap by rememberUpdatedState(newValue = productViewModel.informationProductMap)
    var showProductInfo by remember { mutableStateOf(false) }
    val barcode: State<BarcodeModel> = barcodesFlow.collectAsState()
    val productViewModel = viewModel<ProductViewModel>()

    // Collect the latest barcode data and torch status
    val torchEnabled: State<Boolean> = torchEnabledFlow.collectAsState()

    val context = LocalContext.current


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
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // First Column inside the main Column
            Column(
                modifier = Modifier.weight(1f, false)
            ) {

                // Box composable to display barcode information
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.tertiary)
                        .padding(16.dp)
                ) {
                    // Text displaying barcode information
                    Text(
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.onTertiary,
                        text = "Barcode: ${barcode.value.barcode}\n"
                    )
                }

                // Spacer to add some space between barcode info and the button
                Spacer(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                )
            }

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
                    val text =
                        if (torchEnabled.value) "Turn Off Flashlight" else "Turn On Flashlight"

                    // Text composable inside the Button
                    Text(
                        text = text,
                        Modifier.padding(2.dp), color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Button(onClick = {
                    if (barcode.value.barcode.isNotEmpty()) {
                        productViewModel.getInfo(barcode.value.barcode)
                        showProductInfo = true
                    } else {
                        Toast.makeText(context, "No barcode found", Toast.LENGTH_SHORT).show()

                    }
                }) {
                    Text(text = "Search Product Info")
                }
            }
        }
    }

    if (showProductInfo && informationProductMap.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            //Spacer(modifier = Modifier.height(16.dp))
            //Divider(modifier = Modifier.padding(vertical = 20.dp))

            Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {

                //Title
                val title: String = informationProductMap["title"].toString()
                Text(
                    text = title,
                    modifier = Modifier.padding(16.dp)
                )

                //Image
                ImgDisplay.DisplayPicture(informationProductMap["images"].toString())

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
                    Button(onClick = { showProductInfo = false }) {
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