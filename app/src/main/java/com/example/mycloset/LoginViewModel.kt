package com.example.mycloset

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mycloset.data.LoginUIEvent
import com.example.mycloset.data.LoginUIState
import com.example.mycloset.data.rules.Validator
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {

    var loginUiState = mutableStateOf(LoginUIState())
    var allValidationPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)

    fun onEvent(event: LoginUIEvent){
        validateLoginUIDataWithRules()
        when(event){
            is LoginUIEvent.EmailChanged ->{
               loginUiState.value = loginUiState.value.copy(
                   email = event.email
               )
            }
            is LoginUIEvent.PasswordChanged ->{
                loginUiState.value = loginUiState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked ->{
                login()
            }
        }
    }

    private fun login() {
        loginInProgress.value = true
        val email = loginUiState.value.email
        val password = loginUiState.value.password
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
            loginInProgress.value = false
            }
            .addOnFailureListener{
                loginInProgress.value = false
            }
    }
    private fun validateLoginUIDataWithRules(){
        val emailResult = Validator.validateEmail(
            email = loginUiState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = loginUiState.value.password
        )
        loginUiState.value = loginUiState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )
        allValidationPassed.value = emailResult.status && passwordResult.status
    }

}