
package com.example.mycloset.Views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycloset.DatabaseWorkingset.ProductViewModel
import com.example.mycloset.LoginWorkingSet.LoggedUser
import kotlinx.coroutines.flow.*



//import com.example.mycloset.Screen


//
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyHomeScreen() {
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
                        onClick = { /* Navigate to the scanner screen */ }
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
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: ProductViewModel) {
    // Observe the products list from the ViewModel
    viewModel.getProductsWithEmail(LoggedUser.loggedUserEmail)
    val products = viewModel.products
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
                    onClick = { /* Navigate to the scanner screen
                    navController.navigate(route = Screen.Camera.route)*/}
                ) {
                    Icon(Icons.Default.Camera, contentDescription = null)
                }
            }
        )
    }) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(products.size) { index ->
                val product = products[index]
                ItemCard(product.images, product.title)
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
