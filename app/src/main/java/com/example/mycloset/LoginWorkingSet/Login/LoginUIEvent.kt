package com.example.mycloset.LoginWorkingSet.Login

// Define a sealed class named LoginUIEvent, which represents events related to the login UI.

sealed class LoginUIEvent {
    // Data class representing the event when the email input changes.
    data class EmailChanged(val email: String): LoginUIEvent()

    // Data class representing the event when the password input changes.
    data class PasswordChanged(val password: String): LoginUIEvent()

    // Object representing the event when the login button is clicked.
    object LoginButtonClicked : LoginUIEvent()
}

