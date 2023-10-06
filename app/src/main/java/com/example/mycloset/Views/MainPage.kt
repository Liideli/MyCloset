package com.example.mycloset.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
 Column(modifier = Modifier
     .fillMaxSize()
     .padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
    Text(text = "MyCloset", modifier = Modifier
        .padding(bottom = 16.dp), style = MaterialTheme.typography.displayLarge)

     Spacer(modifier = Modifier.height(16.dp))

     Button(onClick = {}, modifier = Modifier
         .fillMaxWidth()
         .padding(8.dp)) {
         Text(text = "Login")
     }

     Button(onClick = {}, modifier = Modifier
         .fillMaxWidth()
         .padding(8.dp)) {
         Text(text = "Create an account")
     }
 }

}




@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview(){
   Column(modifier = Modifier
       .fillMaxSize()
       .background(MaterialTheme.colorScheme.background)) {
    MainScreen()
   }
}