package com.example.mycloset.Views


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.example.mycloset.ui.theme.md_theme_light_outline

//view used for show all the item fileds and allow to modifie their in real-time
//view used for show all the item fileds and allow to modifie their in real-time
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
                    //title with syle adaptation
                    title = {
                        Text(
                            text = "MyCloset",
                            fontFamily = fontFamily,
                            modifier = Modifier.padding(vertical = 16.dp),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer)
                    },
                    //go bacck to the scan view
                    actions = {
                        IconButton(onClick = { LoginAppRouter.navigateTo(Screen.ProductScanView) }) {
                            Icon(
                                Icons.Default.Camera,
                                contentDescription = "Camera"
                            )
                        }
                        //logout
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
            //start to show the elements
                innerPadding ->
            // Content to be displayed below the top bar
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

                //Spacer(modifier = Modifier.height(24.dp))

                //image
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DisplayPicture(product.images)
                }
                Column (modifier=Modifier.padding(horizontal = 20.dp)){
                    Text(
                        text = "Edit the fields :",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    //only the modificable field are scrollable
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {

                        //prepare the object for save the update
                        var product = products[0]

                        //model
                        //if the field doesn't exist for this product, the application doesnt's show this section
                        if (product.model != "") {
                            item {
                                //this function keep track about all the modification on this field, when you confirm the update the last change applied in this space is use for fill the item field                             EditableTextField(
                                initialValue = product.model,
                                onValueChange = { newValue: String ->
                                    product.model = newValue
                                },
                                "Model"
                                )
                            }
                        }

                        //category
                        item {
                            EditableTextField(
                                initialValue = product.category,
                                onValueChange = { newValue: String ->
                                    product.category = newValue
                                },
                                "Category"
                            )
                        }

                        //brand
                        if (product.brand != "") {
                            item {
                                EditableTextField(
                                    initialValue = product.brand,
                                    onValueChange = { newValue: String ->
                                        product.brand = newValue
                                    },
                                    "Brand"
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
                                    },
                                    "Color"
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
                                    },
                                    "Material"
                                )
                            }
                        }

                        //size
                        if (product.size != "") {
                            item {
                                EditableTextField(
                                    initialValue = product.size,
                                    onValueChange = { newValue: String ->
                                        product.size = newValue
                                    }
                                    ,
                                    "Size"
                                )
                            }
                        }

                    }
                }

                //button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        //go bacck and don't save the changes
                        onClick = {
                            LoginAppRouter.navigateTo(Screen.SingleItemScreen)
                        }
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        //save the changes
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
}



