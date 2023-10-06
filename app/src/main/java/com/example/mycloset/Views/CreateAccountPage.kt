package com.example.mycloset.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycloset.R
import com.example.mycloset.components.ButtonComponent
import com.example.mycloset.components.HeadingTextComponent
import com.example.mycloset.components.NormalTextComponent
import com.example.mycloset.components.PasswordTextField
import com.example.mycloset.components.TextField
import com.example.mycloset.data.SignupViewModel
import com.example.mycloset.data.SignupUIEvent


@Composable
fun SignUpScreen(signupViewModel: SignupViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                NormalTextComponent(value = stringResource(id = R.string.hello))
                HeadingTextComponent(value = stringResource(id = R.string.create_account))
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    lableValue = stringResource(id = R.string.first_name),
                    onTextSelected = { signupViewModel.onEvent(SignupUIEvent.FirstNameChanged(it)) },
                    errorStatus = signupViewModel.registrationUIState.value.firtNameError


                )
                TextField(lableValue = stringResource(id = R.string.last_name), onTextSelected = {
                    signupViewModel.onEvent(SignupUIEvent.LastNameChanged(it))
                }, errorStatus = signupViewModel.registrationUIState.value.lastNameError)
                TextField(lableValue = stringResource(id = R.string.email), onTextSelected = {
                    signupViewModel.onEvent(SignupUIEvent.EmailChanged(it))
                }, errorStatus = signupViewModel.registrationUIState.value.emailError)
                PasswordTextField(
                    lableValue = stringResource(id = R.string.password),
                    onTextSelected = {
                        signupViewModel.onEvent(SignupUIEvent.PasswordChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.passwordError
                )
                Spacer(modifier = Modifier.height(20.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.register), onButtonClicked = {
                        signupViewModel.onEvent(SignupUIEvent.RegisterButtonClicked)
                    },
                    isEnabled = signupViewModel.allValidationPassed.value
                )
            }

        }

    }
    if (signupViewModel.signUpInProgress.value) {
        CircularProgressIndicator()
    }
}


@Preview
@Composable
fun SignUpScreenPreview(){
    SignUpScreen()
}

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen() {
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

        Spacer(modifier = Modifier.height(16.dp))


        TextField(
            value = "",
            onValueChange = {  },
            placeholder = { Text(text = "Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )


        TextField(
            value = "",
            onValueChange = { },
            placeholder = { Text(text = "Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )



        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Create Account")
        }
    }
}*/

/*@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CreateAccountScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        CreateAccountScreen()
    }
}*/
