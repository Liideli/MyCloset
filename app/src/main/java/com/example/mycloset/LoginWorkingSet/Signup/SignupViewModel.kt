package com.example.mycloset.LoginWorkingSet.Signup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mycloset.Rules.Validator
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

// Define a ViewModel class for managing signup-related functionality.
class SignupViewModel : ViewModel() {
    private val TAG = SignupViewModel::class.simpleName

    // Define mutable state variables to hold the current UI state.
    var registrationUIState = mutableStateOf(RegistrationUIState())
    var allValidationPassed = mutableStateOf(false)
    var signUpInProgress = mutableStateOf(false)

    // Function to handle UI events triggered by the user.
    fun onEvent(event: SignupUIEvent) {
        // Validate the registration data based on predefined rules.
        validateDataWithRules()
        when (event) {
            is SignupUIEvent.FirstNameChanged -> {
                // Update the first name field in the UI state.
                registrationUIState.value = registrationUIState.value.copy(
                    firstName = event.firstName
                )
                // Log the current state for debugging.
                printState()
            }
            is SignupUIEvent.LastNameChanged -> {
                // Update the last name field in the UI state.
                registrationUIState.value = registrationUIState.value.copy(
                    lastName = event.lastname
                )
                // Log the current state for debugging.
                printState()
            }
            is SignupUIEvent.EmailChanged -> {
                // Update the email field in the UI state.
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
                // Log the current state for debugging.
                printState()
            }
            is SignupUIEvent.PasswordChanged -> {
                // Update the password field in the UI state.
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
                // Log the current state for debugging.
                printState()
            }
            is SignupUIEvent.RegisterButtonClicked -> {
                // Initiate the signup process.
                signUp()
            }
        }
    }

    // Function to log the current UI state for debugging.
    private fun printState() {
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, registrationUIState.value.toString())
    }

    // Function to initiate the signup process.
    private fun signUp() {
        // Create a user in Firebase with the provided email and password.
        createUserInFirebase(email = registrationUIState.value.email, password = registrationUIState.value.password)
        Log.d(TAG, "Inside_signUp")
    }

    // Function to validate the registration data with predefined rules.
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
        // Update the UI state with validation results.
        registrationUIState.value = registrationUIState.value.copy(
            firstNameError = fnameResult.status,
            lastNameError = lnameResult.status,
            emailError = email.status,
            passwordError = password.status
        )
        // Update the variable to track if all validation rules passed.
        allValidationPassed.value = fnameResult.status && lnameResult.status && email.status && password.status
    }

    // Function to create a user in Firebase with email and password.
    private fun createUserInFirebase(email: String, password: String) {
        signUpInProgress.value = true
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() {
                signUpInProgress.value = false
                if (it.isSuccessful) {
                    // Navigate to the home screen upon successful signup.
                    LoginAppRouter.navigateTo(Screen.HomeScreen)
                }
                Log.d(TAG, "Inside_OnCompleteListener")
                Log.d(TAG, "isSuccessful = ${it.isSuccessful}")
            }
            .addOnFailureListener {
                Log.d(TAG, "Inside_OnFailureListener")
                Log.d(TAG, "Exception = ${it.message}")
                Log.d(TAG, "Exception = ${it.localizedMessage}")
            }
    }

    // Function to log out the current user.
    fun logout() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        val authStateListener = AuthStateListener {
            if (it.currentUser == null) {
                // Navigate to the login screen upon successful sign out.
                LoginAppRouter.navigateTo(Screen.LoginScreen)
                Log.d(TAG, "Inside sign outSuccess")
            } else {
                Log.d(TAG, "Inside sign out is not complete")
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }
}
