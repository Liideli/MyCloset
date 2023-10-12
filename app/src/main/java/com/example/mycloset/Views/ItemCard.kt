package com.example.mycloset.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.mycloset.DatabaseWorkingset.ProductViewModel
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen
import com.example.mycloset.ui.theme.md_theme_dark_onTertiaryContainer
import com.example.mycloset.ui.theme.md_theme_dark_secondaryContainer
import com.example.mycloset.ui.theme.md_theme_light_outline
import com.example.mycloset.ui.theme.md_theme_light_primary
import com.example.mycloset.ui.theme.md_theme_light_primaryContainer
import com.example.mycloset.ui.theme.md_theme_light_secondaryContainer
import com.example.mycloset.ui.theme.textType


data class Item(val id: Int, val title: String, val imageUrl: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(
    imageUrl: String?,
    name: String?,
    barcodeNumber: String,
    productViewModel: ProductViewModel
//    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .background(md_theme_light_outline),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            productViewModel.setSelectedProduct(barcodeNumber)
            LoginAppRouter.navigateTo(Screen.SingleItemScreen)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .background(md_theme_light_secondaryContainer),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .aspectRatio(16f / 9f)
                    .background(md_theme_light_secondaryContainer)
            )
            name?.let { Text(text = it, style = textType.bodyMedium) }
        }
    }
}


/*
@Preview
@Composable
fun MyPrev() {
    ItemCard("https://media.istockphoto.com/id/1303978937/fi/valokuva/valkoinen-lenkkari-sinisell%C3%A4-kaltevuustaustalla-miesten-muoti-urheilukenk%C3%A4-lenkkarit.jpg?s=612x612&w=0&k=20&c=X_lwi6td_xtFUGXjOmAU8WzH-MKPZ-OeWKtKUshe-SI=", "nike")
}
*/