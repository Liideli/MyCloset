package com.example.mycloset.LoginWorkingSet.Login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mycloset.LoginWorkingSet.LoggedUser
import com.example.mycloset.Rules.Validator
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

// Define a ViewModel class for managing login-related functionality.
class LoginViewModel : ViewModel() {

    // Define mutable state variables to hold the current UI state.
    var loginUiState = mutableStateOf(LoginUIState())
    var allValidationPassed = mutableStateOf(false)

    // Variable to track whether a login operation is in progress.
    var loginInProgress = mutableStateOf(false)

    // Function to handle UI events triggered by the user.
    fun onEvent(event: LoginUIEvent) {
        // Validate the login UI data based on predefined rules.
        validateLoginUIDataWithRules()
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                // Update the email field in the UI state.
                loginUiState.value = loginUiState.value.copy(
                    email = event.email
                )
            }
            is LoginUIEvent.PasswordChanged -> {
                // Update the password field in the UI state.
                loginUiState.value = loginUiState.value.copy(
                    password = event.password
                )
            }
            is LoginUIEvent.LoginButtonClicked -> {
                // Initiate the login process.
                login()
            }
        }
    }

    // Function to perform the login operation.
    private fun login() {
        // Set login in progress to true to indicate the login attempt.
        loginInProgress.value = true
        val email = loginUiState.value.email
        val password = loginUiState.value.password

        // Use Firebase Authentication to sign in with the provided email and password.
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    // If login is successful, set the logged-in user's email.
                    LoggedUser.loggedUserEmail = loginUiState.value.email
                    Log.i("EMAIL", loginUiState.value.email)
                    loginInProgress.value = false
                    // Navigate to the home screen upon successful login.
                    LoginAppRouter.navigateTo(Screen.HomeScreen)
                    Log.i("SUCCESS", "Login success")
                }
            }
            .addOnFailureListener { it ->
                // If login fails, handle the failure.
                loginInProgress.value = false
                Log.i("FAIL", "Login failed")
            }
    }

    // Function to validate the login UI data based on predefined rules.
    private fun validateLoginUIDataWithRules() {
        // Validate the email and password, updating the UI state accordingly.
        val emailResult = Validator.validateEmail(
            email = loginUiState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = loginUiState.value.password
        )
        // Update the UI state with validation results.
        loginUiState.value = loginUiState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )
        // Update the variable to track if all validation rules passed.
        allValidationPassed.value = emailResult.status && passwordResult.status
    }
}

/* To check if user has added items, depends on that it will navigate to the HomeScreen or EmptyHomeScreen

class YourViewModel(/*private val userRepository: our data source*/) : ViewModel() {

    // LiveData to hold the result of the item check
    // You can use LiveData or State in Jetpack Compose
    val hasItems = MutableLiveData<Boolean>()

    // Function to check if the user has added items
    fun checkUserItems(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.getUserById(userId)
            val hasItems = user?.items?.isNotEmpty() ?: false
            this@YourViewModel.hasItems.postValue(hasItems)
        }
    }
}

//LoginActivity after pressing the button "LogIn":
viewModel.checkUserItems(userId)

// Observe the hasItems LiveData to handle navigation
viewModel.hasItems.observe(this) { hasItems ->
    if (hasItems) {
        // Navigate to HomeScreen with items
        navController.navigate("homeScreen")
    } else {
        // Navigate to EmptyHomeScreen
        navController.navigate("emptyHomeScreen")
    }
}
 */


