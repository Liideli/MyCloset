package com.example.mycloset.Views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mycloset.DatabaseWorkingset.ProductViewModel
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleItemCard(
    imageUrl: String?,
    model: String?,
    name: String?,
    category: String?,
    brand: String?,
    size: String?,
    productViewModel: ProductViewModel
//    onClick: () -> Unit
) {
    val context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ),
        modifier = Modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(10.dp),

        ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(300.dp)
                        .clip(RoundedCornerShape(18.dp)),
                )
            }
            Divider(Modifier.padding(8.dp))
            Text(
                text = "Product Information:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            name?.let { Text(text = "Name: $it") }

            model?.let { Text(text = "Model: $it") }

            category?.let { Text(text = "Category: $it") }

            brand?.let { Text(text = "Brand: $it") }

            size?.let { Text(text = "Size: $it") }

            Divider(Modifier.padding(8.dp))

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
