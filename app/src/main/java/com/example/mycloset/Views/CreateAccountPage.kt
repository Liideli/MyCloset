package com.example.mycloset.Views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycloset.LoginWorkingSet.Signup.SignupUIEvent
import com.example.mycloset.LoginWorkingSet.Signup.SignupViewModel
import com.example.mycloset.R
import com.example.mycloset.components.ButtonComponent
import com.example.mycloset.components.ClickableTextComponent
import com.example.mycloset.components.DividerTextComponent
import com.example.mycloset.components.NormalTextComponent
import com.example.mycloset.components.PasswordTextField
import com.example.mycloset.components.TextField
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen
import com.example.mycloset.ui.theme.fontFamily

/* View used for registration. */
@Composable
fun SignUpScreen(signupViewModel: SignupViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp), // Adjust the height of the image container as needed
                    contentAlignment = Alignment.TopCenter
                ) {
                    // Add your image here

                }
                Spacer(modifier = Modifier.padding(24.dp))
                Text(
                    fontFamily = fontFamily,
                    text = "MyCloset",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center // Center align the text
                )
                NormalTextComponent(value = stringResource(id = R.string.create_account))
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    lableValue = stringResource(id = R.string.first_name),
                    imageVector = Icons.Default.Person,
                    onTextSelected = { signupViewModel.onEvent(SignupUIEvent.FirstNameChanged(it)) },
                    errorStatus = signupViewModel.registrationUIState.value.firstNameError
                )
                if (!signupViewModel.registrationUIState.value.firstNameError){
                    Text(
                        text = "Pleace enter a first name",
                        color = Color.Red
                    )
                }
                TextField(lableValue = stringResource(id = R.string.last_name),
                    imageVector = Icons.Default.Person,
                    onTextSelected = {
                    signupViewModel.onEvent(SignupUIEvent.LastNameChanged(it))
                }, errorStatus = signupViewModel.registrationUIState.value.lastNameError)
                if (!signupViewModel.registrationUIState.value.lastNameError){
                    Text(
                        text = "Pleace enter a last name",
                        color = Color.Red
                    )
                }
                TextField(lableValue = stringResource(id = R.string.email),
                    imageVector = Icons.Default.Email,
                    onTextSelected = {
                    signupViewModel.onEvent(SignupUIEvent.EmailChanged(it))
                }, errorStatus = signupViewModel.registrationUIState.value.emailError)
                if (!signupViewModel.registrationUIState.value.emailError){
                    Text(
                        text = "Pleace enter a valid email",
                        color = Color.Red
                    )
                }
                PasswordTextField(
                    labelValue = stringResource(id = R.string.password),
                    onTextSelected = {
                        signupViewModel.onEvent(SignupUIEvent.PasswordChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.passwordError
                )
                if (!signupViewModel.registrationUIState.value.passwordError){
                    Text(
                        text = "Pleace enter a password with min 6 characters",
                        color = Color.Red
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.register), onButtonClicked = {
                        signupViewModel.onEvent(SignupUIEvent.RegisterButtonClicked)
                    },
                    isEnabled = signupViewModel.allValidationPassed.value
                )
                DividerTextComponent()
                ClickableTextComponent(tryingToLogin = true, onTextSelected = {
                    LoginAppRouter.navigateTo(Screen.LoginScreen)
                })
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


