package com.example.mycloset.LoginWorkingSet.Login

// Define a data class named LoginUIState that represents the state of a login user interface.

data class LoginUIState (
    // The current user's email input.
    var email: String = "",

    // The current user's password input.
    var password: String = "",

    // Indicates whether there is an error with the email input.
    var emailError: Boolean = false,

    // Indicates whether there is an error with the password input.
    var passwordError: Boolean = false
)
