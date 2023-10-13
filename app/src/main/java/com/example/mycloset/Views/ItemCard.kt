package com.example.mycloset.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.mycloset.DatabaseWorkingset.ProductViewModel
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen
import com.example.mycloset.ui.theme.textType

//used for store the data that the applciation will shiow in the card
data class Item(val id: Int, val title: String, val imageUrl: String)

//Card usedd for showing in a list the single item with their name and image in the home
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(
    imageUrl: String?,
    name: String?,
    barcodeNumber: String,
    productViewModel: ProductViewModel
) {
    
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier.padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            productViewModel.setSelectedProduct(barcodeNumber)
            LoginAppRouter.navigateTo(Screen.SingleItemScreen)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //image
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            //name
            name?.let { Text(text = it, style = textType.bodyMedium) }
        }
    }
}
