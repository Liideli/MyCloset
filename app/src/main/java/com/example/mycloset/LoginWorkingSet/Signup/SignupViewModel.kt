package com.example.mycloset.LoginWorkingSet.Signup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mycloset.Rules.Validator
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

class SignupViewModel: ViewModel() {

    private val TAG = SignupViewModel::class.simpleName

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
                printState()
            }
            is SignupUIEvent.LastNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    lastName = event.lastname
                )
                printState()
            }
            is SignupUIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
                printState()
            }
            is SignupUIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
                printState()
            }
            is SignupUIEvent.RegisterButtonClicked -> {
                signUp()
            }
        }
    }

    private fun printState(){
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, registrationUIState.value.toString())
    }

    private fun signUp() {
        createUserInFirebase(email = registrationUIState.value.email, password = registrationUIState.value.password )
        Log.d(TAG, "Inside_signUp")
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
            firstNameError = fnameResult.status,
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
                if (it.isSuccessful){
                    LoginAppRouter.navigateTo(Screen.HomeScreen)
                }
                Log.d(TAG, "Inside_OnCompleteListener")
                Log.d(TAG, "isSuccessful = ${it.isSuccessful}")

            }
            .addOnFailureListener{
                Log.d(TAG, "Inside_OnFailureListener")
                Log.d(TAG, "Exception = ${it.message}")
                Log.d(TAG, "Exception = ${it.localizedMessage}")
            }
    }
    fun logout(){
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        val  authStateListener = AuthStateListener{
            if (it.currentUser == null){
                LoginAppRouter.navigateTo(Screen.LoginScreen)
                Log.d(TAG, "Inside sign outSuccess")
            } else{
                Log.d(TAG, "Inside sign out is not complete")
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }
}