package com.example.mycloset.LoginWorkingSet.Login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mycloset.LoginWorkingSet.LoggedUser
import com.example.mycloset.Rules.Validator
import com.example.mycloset.navigation.LoginAppRouter
import com.example.mycloset.navigation.Screen
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

                if (it.isSuccessful){
                    //if the login work, i set the object that contain the email(i need it for view, add, delete the product from a specific user)
                    LoggedUser.loggedUserEmail =loginUiState.value.email

                    loginInProgress.value = false
                    LoginAppRouter.navigateTo(Screen.HomeScreen)
                }
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


