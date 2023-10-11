package com.example.mycloset.LoginWorkingSet.Login

data class LoginUIState (
    var email: String = "",
    var password: String = "",

    var emailError: Boolean = false,
    var passwordError: Boolean = false
)