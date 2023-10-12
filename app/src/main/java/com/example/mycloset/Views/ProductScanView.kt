package com.example.mycloset.Views

import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mycloset.ApiWorkingSet.ImgDisplay
import com.example.mycloset.BarcodeWorkingSet.BarcodeModel
import com.example.mycloset.DatabaseWorkingset.ProductViewModel
import com.example.mycloset.LoginWorkingSet.LoggedUser
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen
import kotlinx.coroutines.flow.StateFlow

//@OptIn(ExperimentalMaterial3Api::class)
@OptIn(ExperimentalMaterial3Api::class)
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
    val informationProductObject by rememberUpdatedState(newValue = productViewModel.informationProductObject)

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

    val topAppBarColor = MaterialTheme.colorScheme.onTertiary

    if (!showProductInfo) {
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text("MyCloset", modifier = Modifier.padding(2.dp))
                },
                actions = {
                    IconButton(
                        onClick = {
                            LoginAppRouter.navigateTo(Screen.HomeScreen)
                        }
                    ) {

                        Icon(Icons.Default.Home, contentDescription = "Camera")
                    }
                }
            )
        }) { innerPadding ->
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
                    .padding(innerPadding)
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
    }

    if (informationProductObject.barcodeNumber != "") {
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
    if (showProductInfo && informationProductObject.barcodeNumber != "") {
        isLoading = false
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text("MyCloset", modifier = Modifier.padding(2.dp))
                },
                actions = {
                    IconButton(
                        onClick = {
                            showProductInfo=false
                        }
                    ) {
                        Icon(Icons.Default.Camera, contentDescription = "Camera")
                    }
                }
            )
        }) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(18.dp)
            ) {

                //If the title is to big, make it shorter
                val title = informationProductObject.title
                val textToDisplay = if ("," in title) {
                    title.substringBefore(",")
                } else {
                    if ("-" in title) {
                        title.substringBefore("-")
                    } else {
                        title
                    }
                }
                //Title
                Text(
                    text = textToDisplay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Image of the product
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    ImgDisplay.DisplayPicture(informationProductObject.images)
                }

                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {

                    //information
                    item {
                        Text(
                            text = "Product Information :",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    //barcode number
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant) // Colore di sfondo personalizzato per Model
                        ) {
                            Text("Barcode: ${informationProductObject.barcodeNumber}")
                        }
                    }

                    //model
                    if(informationProductObject.model!=""){
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                        .background(MaterialTheme.colorScheme.surfaceVariant) // Colore di sfondo personalizzato per Model
                                ) {
                                Text("Model: ${informationProductObject.model}")
                            }
                        }
                    }

                    //category
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant) // Colore di sfondo personalizzato per Model
                        ) {
                        val categoryText = informationProductObject.category
                        val category = categoryText.substringAfterLast(">")
                        Text("Category: $category")
                        }
                    }

                    //brand
                    if(informationProductObject.brand!=""){
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant) // Colore di sfondo personalizzato per Model
                            ) {
                                Text("Brand: ${informationProductObject.brand}")
                            }
                        }
                    }

                    //color
                    if(informationProductObject.color!="") {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant) // Colore di sfondo personalizzato per Model
                            ) {
                                Text("Color: ${informationProductObject.color}")
                            }
                        }
                    }

                    //material
                    if(informationProductObject.material!="") {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant) // Colore di sfondo personalizzato per Model
                            ) {
                                Text("Material: ${informationProductObject.material}")
                            }
                        }
                    }

                    //size
                    if(informationProductObject.size!=""){
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant) // Colore di sfondo personalizzato per Model
                            ) {
                                Text("Size: ${informationProductObject.size}")
                            }
                        }
                    }

                }

                Spacer(modifier = Modifier.weight(1f))

                //button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        showProductInfo = false
                    }) {
                        Text(text = "Back")
                    }
                    if (LoggedUser.loggedUserEmail != "") {
                        Button(onClick = {
                            productViewModel.saveToDatabase(informationProductObject)
                            Toast.makeText(context, "Item added!", Toast.LENGTH_SHORT).show()
                            LoginAppRouter.navigateTo(Screen.HomeScreen)
                        }) {
                            Text(text = "Add")
                        }
                    } else {
                        Log.i(
                            "LOGIN_ERROR",
                            "You can't add a new item to your wardrobe because you aren't logged"
                        )
                    }
                }
            }
        }
    }
}

