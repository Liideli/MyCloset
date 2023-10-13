package com.example.mycloset.Views

import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mycloset.DatabaseWorkingset.ProductViewModel
import com.example.mycloset.LoginWorkingSet.Signup.SignupViewModel
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen
import com.example.mycloset.ui.theme.fontFamily
import com.example.mycloset.ui.theme.md_theme_light_outline

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun SingleItemScreen(productViewModel: ProductViewModel) {
    val viewModel: SignupViewModel = viewModel()
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
                        Text(
                            text = "MyCloset",
                            fontFamily = fontFamily,
                            modifier = Modifier.padding(vertical = 16.dp),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer)
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
        ) {
                innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(md_theme_light_outline)
            ) {
                val product = products[0]

                //title
                Text(
                    text = product.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
                )

                //images
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //Log.i("IMAGE",product.images)
                    //DisplayPicture(product.images)
                    Image(
                        painter = rememberAsyncImagePainter(product.images),
                        contentDescription = null,
                        modifier = Modifier
                            .size(300.dp)
                            .clip(RoundedCornerShape(18.dp)),
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))


                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {

                    //product information
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
                            Text("Barcode: ${product.barcodeNumber}")
                        }
                    }

                    //model
                    if (product.model != "") {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant) // Colore di sfondo personalizzato per Model
                            ) {
                                Text("Model: ${product.model}")
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
                            Text("Category: ${product.category}")
                        }
                    }

                    //brand
                    if (product.brand != "") {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant) // Colore di sfondo personalizzato per Model
                            ) {
                                Text("Brand: ${product.brand}")
                            }
                        }
                    }

                    //color
                    if (product.color != "") {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant) // Colore di sfondo personalizzato per Model
                            ) {
                                Text("Color: ${product.color}")
                            }
                        }
                    }

                    //material
                    if (product.material != "") {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant) // Colore di sfondo personalizzato per Model
                            ) {
                                Text("Material: ${product.material}")
                            }
                        }
                    }

                    //size
                    if (product.size != "") {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant) // Colore di sfondo personalizzato per Model
                            ) {
                                Text("Size: ${product.size}")
                            }
                        }
                    }

                }

                Spacer(modifier = Modifier.weight(1f))


                //buttons
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
                            LoginAppRouter.navigateTo(Screen.UpdateSingleScreen)
                        }
                    ) {
                        Text("Update Item")
                    }
                }
            }
        }
    }
}