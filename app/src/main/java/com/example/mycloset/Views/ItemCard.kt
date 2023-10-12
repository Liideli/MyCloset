package com.example.mycloset.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.mycloset.DatabaseWorkingset.ProductViewModel
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(
    imageUrl: String?,
    name: String?,
    barcode:String,
    userEmail:String,
    productViewModel: ProductViewModel
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier.padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            LoginAppRouter.navigateTo(Screen.SingleItemScreen)
        }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            name?.let { Text(text = it) }
        }
    }
}



