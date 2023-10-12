package com.example.mycloset.Views

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycloset.ApiWorkingSet.ImgDisplay.Companion.DisplayPicture
import com.example.mycloset.DatabaseWorkingset.ProductViewModel
import com.example.mycloset.LoginWorkingSet.Signup.SignupViewModel
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen
import com.example.mycloset.ui.theme.fontFamily
import com.example.mycloset.ui.theme.fontFamilyText
import com.example.mycloset.ui.theme.md_theme_light_outline
import com.example.mycloset.ui.theme.textType

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun SingleItemScreen(productViewModel: ProductViewModel) {
    var viewModel: SignupViewModel = viewModel()
    val informationProductObject by rememberUpdatedState(newValue = productViewModel.informationProductObject)
    productViewModel.getProductWithBarcode(productViewModel.selectedProduct)
    val products = productViewModel.products
    val context = LocalContext.current
    Surface(modifier = Modifier.background(md_theme_light_outline)) {
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
                        Text("MyCloset", style = textType.titleLarge)
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
                    .background(md_theme_light_outline)
            ) {
                val product = products[0]

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
                    item {
                        Text(
                            text = "Product Information",
                            style = textType.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    item {
                        Text("Barcode: ${product.barcodeNumber}", style = textType.bodyMedium)
                    }

                    item {
                        Text("Model: ${product.model}", style = textType.bodyMedium)
                    }

                    item {
                        Text("Category: ${product.category}", style = textType.bodyMedium)
                    }

                    item {
                        Text("Brand: ${product.brand}", style = textType.bodyMedium)
                    }

                    item {
                        Text("Color: ${product.color}", style = textType.bodyMedium)
                    }

                    item {
                        Text("Material: ${product.material}", style = textType.bodyMedium)
                    }

                    item {
                        Text("Size: ${product.size}", style = textType.bodyMedium)
                    }

                }

                // Add more text components or other content as needed
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            productViewModel.deleteProduct(productViewModel.selectedProduct)
                            Toast.makeText(context, "Item Deleted!", Toast.LENGTH_SHORT).show()
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
}


/*@Composable
@Preview
fun SingleItemView(){
    SingleItemScreen()
}*/

//@Preview
//@Composable
//fun Info() {
//    SingleItemScreen()
//}