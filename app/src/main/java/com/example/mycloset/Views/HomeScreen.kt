
package com.example.mycloset.Views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycloset.DatabaseWorkingset.ProductViewModel
import com.example.mycloset.LoginWorkingSet.LoggedUser
import com.example.mycloset.LoginWorkingSet.Signup.SignupViewModel
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen
import com.example.mycloset.ui.theme.fontFamily
import kotlinx.coroutines.flow.*

/* View used for showing all the information about products that the user has.
Two different screens: one is used when user had added dome items and another one, when user doesn't have any items.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun HomeScreen(productViewModel: ProductViewModel) {
    val signupViewModel: SignupViewModel = viewModel()
    productViewModel.getProductsWithEmail(LoggedUser.loggedUserEmail)
    val products = productViewModel.products

    if (products.isEmpty()) {
        Scaffold(
            topBar = {
                TopAppBar(
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
                        IconButton(
                            onClick = { LoginAppRouter.navigateTo(Screen.ProductScanView) }
                        ) {
                            Icon(Icons.Default.Camera, contentDescription = "Camera")
                        }
                        IconButton(
                            onClick = {
                                signupViewModel.logout()
                            }
                        ) {
                            Icon(Icons.Default.Logout, contentDescription = "Logout")
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
                    IconButton(
                        onClick = { signupViewModel.logout() }
                    ) {
                        Icon(Icons.Default.Logout, contentDescription = "Logout")
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { LoginAppRouter.navigateTo(Screen.ProductScanView) }
                    ) {
                        Icon(Icons.Default.Camera, contentDescription = "Camera")
                    }
                }
            )
        }
        ) { innerPadding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(products.size) { index ->
                    val product = products[index]
                    ItemCard(product.images, product.title, product.barcodeNumber, productViewModel)
                }
            }
        }
    }
}



