package com.example.mycloset

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.example.mycloset.ImgDisplay.Companion.DisplayPicture
import com.example.mycloset.ui.theme.MyClosetTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.jetbrains.annotations.Async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val productViewModel by viewModels<ProductViewModel>()
        super.onCreate(savedInstanceState)
        setContent {
            MyClosetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductInfoScreen(productViewModel)
                }
            }
        }
    }
}

@Composable
fun ProductInfoScreen(productViewModel: ProductViewModel) {
    val informationProductMap by rememberUpdatedState(newValue = productViewModel.informationProductMap)
    var showProductInfo by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = {
            productViewModel.getInfo()
            showProductInfo = true
        })
        {
            Text("Fetch Product Info")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier.padding(vertical = 20.dp))

        if (showProductInfo) {
            if (informationProductMap.isNotEmpty()) {
                Column {

                    //Title
                    val title: String = informationProductMap["title"].toString()
                    Text(
                        text = title,
                        modifier = Modifier.padding(16.dp)
                    )

                    //Image
                    DisplayPicture(informationProductMap["images"].toString())

                    //Table
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        informationProductMap.forEach { (key, value) ->
                            if (key != "title" && key != "images") {
                                item {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = key, fontWeight = FontWeight.Bold)
                                        Text(text = value.toString())
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }

                    //Button
                    Row {
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Cancel")
                        }
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Add")
                        }

                    }
                }
            }
        }
    }
}




