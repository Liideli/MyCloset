package com.example.mycloset.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycloset.LoginWorkingSet.Login.LoginViewModel
import com.example.mycloset.R
import com.example.mycloset.components.ButtonComponent
import com.example.mycloset.components.ClickableTextComponent
import com.example.mycloset.components.DividerTextComponent
import com.example.mycloset.components.HeadingTextComponent
import com.example.mycloset.components.NormalTextComponent
import com.example.mycloset.components.PasswordTextField
import com.example.mycloset.components.TextField
import com.example.mycloset.LoginWorkingSet.Login.LoginUIEvent
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary,
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp), // Adjust the height of the image container as needed
                contentAlignment = Alignment.TopCenter
            ) {
                // Add your image here
                Image(
                    painter = painterResource(id = R.drawable.t_shirt), // Replace with your image resource
                    contentDescription = "Image",
                    modifier = Modifier.size(50.dp) // Adjust the size of the image as needed
                )
            }
            Spacer(modifier = Modifier.padding(16.dp))
            NormalTextComponent(value = stringResource(id = R.string.my_closet))
            HeadingTextComponent(value = stringResource(id = R.string.welcome_back))

            TextField(lableValue = stringResource(id = R.string.email), onTextSelected = {loginViewModel.onEvent(
                LoginUIEvent.EmailChanged(it))}, errorStatus = loginViewModel.loginUiState.value.emailError , imageVector = Icons.Default.Email)
            PasswordTextField(labelValue = stringResource(id = R.string.password), onTextSelected = {loginViewModel.onEvent(
                LoginUIEvent.PasswordChanged(it))}, errorStatus = loginViewModel.loginUiState.value.passwordError)
            Spacer(modifier = Modifier.height(40.dp))
            ButtonComponent(value = stringResource(id = R.string.login), onButtonClicked = { loginViewModel.onEvent(
                LoginUIEvent.LoginButtonClicked) }, isEnabled = loginViewModel.allValidationPassed.value)
            Spacer(modifier = Modifier.height(20.dp))
            DividerTextComponent()
            ClickableTextComponent(onTextSelected = {LoginAppRouter.navigateTo(Screen.SignupScreen)} , tryingToLogin = false)
        }

    }
}



/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MyCloset",
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.displayLarge
        )
        Text(text = "Welcome back!")

        Spacer(modifier = Modifier.height(16.dp))

        // Username TextField
        TextField(
            value = "",
            onValueChange = { loginViewModel.onEvent(LoginUIEvent.EmailChanged(it)) },
            placeholder = { Text(text = "email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Password TextField
       TextField(
            value = "",
            onValueChange = { loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it)) },
            placeholder = { Text(text = "Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Button(
            onClick = { loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Login")
        }
    }
}
*/


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LoginScreen()
    }
}
