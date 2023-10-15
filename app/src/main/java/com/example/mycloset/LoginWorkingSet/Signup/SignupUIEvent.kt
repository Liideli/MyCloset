package com.example.mycloset.LoginWorkingSet.Signup

// Define a sealed class named SignupUIEvent, which represents events related to the signup UI.

sealed class SignupUIEvent {
    // Data class representing the event when the first name input changes.
    data class FirstNameChanged(val firstName: String): SignupUIEvent()

    // Data class representing the event when the last name input changes.
    data class LastNameChanged(val lastname: String): SignupUIEvent()

    // Data class representing the event when the email input changes.
    data class EmailChanged(val email: String): SignupUIEvent()

    // Data class representing the event when the password input changes.
    data class PasswordChanged(val password: String): SignupUIEvent()

    // Object representing the event when the registration button is clicked.
    object RegisterButtonClicked : SignupUIEvent()
}

