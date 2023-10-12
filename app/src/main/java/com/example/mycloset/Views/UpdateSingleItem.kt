package com.example.mycloset.Views

import android.widget.Toast
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateSingleItem(
    productViewModel: ProductViewModel
) {
    var viewModel: SignupViewModel = viewModel()
    val informationProductObject by rememberUpdatedState(newValue = productViewModel.informationProductObject)
    productViewModel.getProductWithBarcode(productViewModel.selectedProduct)
    val products = productViewModel.products
    val context = LocalContext.current
    //used for keeping trak of the edits
    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            // TopAppBar (the top bar)
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { LoginAppRouter.navigateTo(Screen.HomeScreen) }) {
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
                    IconButton(onClick = { LoginAppRouter.navigateTo(Screen.ProductScanView) }) {
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
    ) { innerPadding ->
        // Content to be displayed below the top bar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            val product = products[0]

            //title
            Text(
                text = product.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )

            Spacer(modifier = Modifier.height(24.dp))

            //image
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DisplayPicture(product.images)
            }

            Spacer(modifier = Modifier.height(24.dp))


            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {

                //info
                item {
                    Text(
                        text = "Edit the fields :",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                //prepare the object for save the update
                var product = products[0]

                //model
                if (product.model != "") {
                    item {
                                EditableTextField(
                                initialValue = product.model,
                                onValueChange = { newValue: String ->
                                    product.model = newValue
                                }
                            )
                        }
                    }

                //category
                item {
                    EditableTextField(
                        initialValue = product.category,
                        onValueChange = { newValue: String ->
                            product.category = newValue
                        }
                    )
                }

                //brand
                if (product.brand != "") {
                    item {
                        EditableTextField(
                            initialValue = product.brand,
                            onValueChange = { newValue: String ->
                                product.brand = newValue
                            }
                        )
                    }
                }

                //color
                if (product.color != "") {
                    item {
                        EditableTextField(
                            initialValue = product.color,
                            onValueChange = { newValue: String ->
                                product.color = newValue
                            }
                        )
                    }
                }

                //material
                if (product.material != "") {
                    item {
                        EditableTextField(
                            initialValue = product.material,
                            onValueChange = { newValue: String ->
                                product.material = newValue
                            }
                        )
                    }
                }

                //size
                if (informationProductObject.size != "") {
                    item {
                        EditableTextField(
                            initialValue = product.size,
                            onValueChange = { newValue: String ->
                                product.size = newValue
                            }
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.weight(1f))

            //bitton
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        LoginAppRouter.navigateTo(Screen.SingleItemScreen)
                    }
                ) {
                    Text("Cancel")
                }
                Button(
                    onClick = {
                        productViewModel.updateProductDetails(product)
                        Toast.makeText(context, "Item Updated!", Toast.LENGTH_SHORT).show()
                        LoginAppRouter.navigateTo(Screen.HomeScreen)
                    }
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}



