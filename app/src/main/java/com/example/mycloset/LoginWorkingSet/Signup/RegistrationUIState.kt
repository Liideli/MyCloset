package com.example.mycloset.LoginWorkingSet.Signup

// Define a data class named RegistrationUIState that represents the state of a registration user interface.

data class RegistrationUIState (
    // The current user's first name input.
    var firstName: String = "",

    // The current user's last name input.
    var lastName: String = "",

    // The current user's email input.
    var email: String = "",

    // The current user's password input.
    var password: String = "",

    // Indicates whether there is an error with the first name input.
    var firstNameError: Boolean = false,

    // Indicates whether there is an error with the last name input.
    var lastNameError: Boolean = false,

    // Indicates whether there is an error with the email input.
    var emailError: Boolean = false,

    // Indicates whether there is an error with the password input.
    var passwordError: Boolean = false
)
