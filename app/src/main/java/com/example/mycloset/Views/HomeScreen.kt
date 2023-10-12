
package com.example.mycloset.Views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycloset.DatabaseWorkingset.ProductViewModel
import com.example.mycloset.LoginWorkingSet.LoggedUser
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen
import kotlinx.coroutines.flow.*


import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mycloset.LoginWorkingSet.Signup.SignupViewModel
import com.example.mycloset.navigation.Screen

//import com.example.mycloset.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: ProductViewModel) {
    // Observe the products list from the ViewModel
    //viewModel.getProducts()
    viewModel.getProductsWithEmail(LoggedUser.loggedUserEmail)
    val products = viewModel.products
    Log.i("PROD", products.toString())
    if (products.isEmpty()) {
        Scaffold(
            topBar = {
                TopAppBar(
//                colors = topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.primary,
//                ),
                    title = {
                        Text("MyCloset")
                    }, actions = {
                        IconButton(
                            onClick = { LoginAppRouter.navigateTo(Screen.ProductScanView) }
                        ) {
                            Icon(Icons.Default.Camera, contentDescription = null)
                        }
                    }
                )
            }) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "You don't have any items yet...",
                    textAlign = TextAlign.Center,
                )
            }
        }
    } else {
    Scaffold(topBar = {
        TopAppBar(
//                colors = topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.primary,
//                ),
            title = {
                Text("MyCloset", modifier = Modifier.padding(2.dp))
            }, actions = {
                IconButton(
                    onClick = { LoginAppRouter.navigateTo(Screen.ProductScanView)}
                ) {
                    Icon(Icons.Default.Camera, contentDescription = null)
                }
            }
        )
    }) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            items(products.size) { index ->
                val product = products[index]
                ItemCard(product.images, product.title)
            }
        }
    }
    }
}

/*
@Preview
@Composable
fun PrevHome() {
    HomeScreen(
        navController = rememberNavController()
    )
}

@Preview
@Composable
fun PreEmpty() {
    EmptyHomeScreen()
}
*/
