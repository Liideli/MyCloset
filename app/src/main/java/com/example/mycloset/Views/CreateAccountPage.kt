package com.example.mycloset.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycloset.R
import com.example.mycloset.components.ButtonComponent
import com.example.mycloset.components.ClickableTextComponent
import com.example.mycloset.components.DividerTextComponent
import com.example.mycloset.components.HeadingTextComponent
import com.example.mycloset.components.NormalTextComponent
import com.example.mycloset.components.PasswordTextField
import com.example.mycloset.components.TextField
import com.example.mycloset.data.SignupViewModel
import com.example.mycloset.data.SignupUIEvent
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen


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
                HeadingTextComponent(value = stringResource(id = R.string.my_closet))
                NormalTextComponent(value = stringResource(id = R.string.create_account))
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    lableValue = stringResource(id = R.string.first_name),
                    imageVector = Icons.Default.Person,
                    onTextSelected = { signupViewModel.onEvent(SignupUIEvent.FirstNameChanged(it)) },
                    errorStatus = signupViewModel.registrationUIState.value.firstNameError


                )
                TextField(lableValue = stringResource(id = R.string.last_name),
                    imageVector = Icons.Default.Person,
                    onTextSelected = {
                    signupViewModel.onEvent(SignupUIEvent.LastNameChanged(it))
                }, errorStatus = signupViewModel.registrationUIState.value.lastNameError)
                TextField(lableValue = stringResource(id = R.string.email),
                    imageVector = Icons.Default.Email,
                    onTextSelected = {
                    signupViewModel.onEvent(SignupUIEvent.EmailChanged(it))
                }, errorStatus = signupViewModel.registrationUIState.value.emailError)
                PasswordTextField(
                    labelValue = stringResource(id = R.string.password),
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


