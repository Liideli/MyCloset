package com.example.mycloset.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mycloset.data.rules.Validator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

class SignupViewModel: ViewModel() {

 var registrationUIState = mutableStateOf(RegistrationUIState())
 var allValidationPassed = mutableStateOf(false)
    var signUpInProgress = mutableStateOf(false)
    fun onEvent(event: SignupUIEvent){
        validateDataWithRules()
        when(event){
            is SignupUIEvent.FirstNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    firstName = event.firstName
                )
            }
            is SignupUIEvent.LastNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    lastName = event.lastname
                )
            }
            is SignupUIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
            }
            is SignupUIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
            }
            is SignupUIEvent.RegisterButtonClicked -> {
                signUp()
            }
        }
    }

    private fun signUp() {
        createUserInFirebase(email = registrationUIState.value.email, password = registrationUIState.value.password )
    }

    private fun validateDataWithRules() {
        val fnameResult = Validator.validateFirstName(
            fName = registrationUIState.value.firstName
        )
        val lnameResult = Validator.validateLastName(
            lname = registrationUIState.value.lastName
        )
        val email = Validator.validateEmail(
            email = registrationUIState.value.email
        )
        val password = Validator.validatePassword(
            password = registrationUIState.value.password
        )
        registrationUIState.value = registrationUIState.value.copy(
            firtNameError = fnameResult.status,
            lastNameError = lnameResult.status,
            emailError = email.status,
            passwordError = password.status
        )
        allValidationPassed.value = fnameResult.status && lnameResult.status && email.status && password.status
    }
    private fun createUserInFirebase(email: String, password: String){
        signUpInProgress.value = true
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(){
            signUpInProgress.value = false
            }
            .addOnFailureListener{

            }
    }
    fun logout(){
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        val  authStateListener = AuthStateListener{
            if (it.currentUser == null){

            }
        }
        firebaseAuth.addAuthStateListener { authStateListener }
    }
}