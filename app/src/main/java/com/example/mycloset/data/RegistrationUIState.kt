package com.example.mycloset.data

data class RegistrationUIState (
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",

    var firtNameError: Boolean = false,
    var lastNameError: Boolean = false,
    var emailError: Boolean = false,
    var passwordError: Boolean = false
)