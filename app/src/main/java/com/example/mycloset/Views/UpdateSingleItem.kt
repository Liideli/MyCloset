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
    productViewModel: ProductViewModel,
) {
    var editedProduct by remember { mutableStateOf(productViewModel.informationProductObject.copy()) }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            // TopAppBar (the top bar)
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text("Edit Product")
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            DisplayPicture(editedProduct.images)

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Edit Product Information",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            TextField(
                value = editedProduct.model,
                onValueChange = { editedProduct.model = it },
                label = { Text("Model") }
            )

            TextField(
                value = editedProduct.category,
                onValueChange = { editedProduct.category = it },
                label = { Text("Category") }
            )

            TextField(
                value = editedProduct.brand,
                onValueChange = { editedProduct.brand = it },
                label = { Text("Brand") }
            )

            TextField(
                value = editedProduct.color,
                onValueChange = { editedProduct.color = it },
                label = { Text("Color") }
            )

            TextField(
                value = editedProduct.material,
                onValueChange = { editedProduct.material = it },
                label = { Text("Material") }
            )

            TextField(
                value = editedProduct.size,
                onValueChange = { editedProduct.size = it },
                label = { Text("Size") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {LoginAppRouter.navigateTo(Screen.SingleItemScreen) }
                ) {
                    Text("Cancel")
                }
                Button(
                    onClick = {
                        // Save the edited product
                        productViewModel.updateProductDetails(editedProduct)
                        Toast.makeText(context, "Item Delated!", Toast.LENGTH_SHORT).show()
                        LoginAppRouter.navigateTo(Screen.SingleItemScreen)
                    }
                ) {
                    Text("Save")
                }

            }
        }
    }
}