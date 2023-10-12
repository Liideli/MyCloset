package com.example.mycloset.Views

import android.widget.Toast
import androidx.camera.view.LifecycleCameraController
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycloset.ApiWorkingSet.ImgDisplay
import com.example.mycloset.ApiWorkingSet.ImgDisplay.Companion.DisplayPicture
import com.example.mycloset.BarcodeWorkingSet.BarcodeModel
import com.example.mycloset.DatabaseWorkingset.ProductEntity
import com.example.mycloset.LoginWorkingSet.Signup.SignupViewModel
import com.example.mycloset.DatabaseWorkingset.ProductViewModel
import com.example.mycloset.LoginWorkingSet.LoggedUser
import com.example.mycloset.R
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun SingleItemScreen(productViewModel: ProductViewModel) {
    var viewModel: SignupViewModel = viewModel()
    val informationProductObject by rememberUpdatedState(newValue = productViewModel.informationProductObject)
    val context = LocalContext.current

    Scaffold(
        topBar = {
            // TopAppBar (the top bar)
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text("MyCloset")
                },
                actions = {
                    IconButton(onClick = { /* Handle camera navigation here */ }) {
                        Icon(
                            Icons.Default.Camera,
                            contentDescription = "Camera"
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.logout()
                        }
                    ) {
                        Icon(Icons.Default.Logout, contentDescription = "Logout")
                    }
                }
            )
        }
    ) { padding ->
        // Content to be displayed below the top bar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            DisplayPicture(informationProductObject.images)

            Spacer(modifier = Modifier.height(24.dp))


            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    Text(
                        text = "Product Information",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                item {
                    Text("Barcode: ${informationProductObject.barcodeNumber}")
                }

                item {
                    Text("Model: ${informationProductObject.model}")
                }

                item {
                    Text("Category: ${informationProductObject.category}")
                }

                item {
                    Text("Brand: ${informationProductObject.brand}")
                }

                item {
                    Text("Color: ${informationProductObject.color}")
                }

                item {
                    Text("Material: ${informationProductObject.material}")
                }

                item {
                    Text("Size: ${informationProductObject.size}")
                }

            }


            Spacer(modifier = Modifier.height(16.dp))

            // Add more text components or other content as needed
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        productViewModel.deleteProduct(informationProductObject)
                        Toast.makeText(context, "Item Delated!", Toast.LENGTH_SHORT).show()
                        LoginAppRouter.navigateTo(Screen.HomeScreen)
                    }
                ) {
                    Text("Delete Item")
                }
                Button(
                    onClick = {

                    }
                ) {
                    Text("Update Item")
                }
            }
        }
    }
}
