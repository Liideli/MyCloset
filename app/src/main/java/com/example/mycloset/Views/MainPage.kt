package com.example.mycloset.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycloset.R
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen
import com.example.mycloset.ui.theme.fontFamily
import com.example.mycloset.ui.theme.textType

/* View used for the main screen with application's logo and next navigation to log in or to register a new account */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary, // Set the background color of the Surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo and title
            Image(
                painter = painterResource(id = R.drawable.t_shirt), // Replace with your logo resource
                contentDescription = "Logo",
                modifier = Modifier
                    .size(150.dp)
                    .padding(top = 36.dp)
            )
            Text(
                fontFamily = fontFamily,
                text = "MyCloset",
                modifier = Modifier.padding(vertical = 16.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Buttons
            Button(
                onClick = { LoginAppRouter.navigateTo(Screen.LoginScreen) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(50.dp),
            ) {
                Text(text = "Login", style = textType.bodyLarge)
            }

            Button(
                onClick = { LoginAppRouter.navigateTo(Screen.SignupScreen) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(50.dp),
            ) {
                Text(text = "Create an account", style = textType.bodyLarge)
            }
        }
    }
}





@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview(){

    MainScreen()
   }
