package com.example.mycloset.Rules

// Define an object named Validator to provide validation functions for various input fields.
object Validator {
    // Function to validate the first name.
    fun validateFirstName(fName: String): ValidationResult {
        // Return a ValidationResult with a status indicating whether the first name is valid.
        return ValidationResult(!fName.isNullOrEmpty() && fName.length >= 4)
    }

    // Function to validate the last name.
    fun validateLastName(lname: String): ValidationResult {
        // Return a ValidationResult with a status indicating whether the last name is valid.
        return ValidationResult(!lname.isNullOrEmpty() && lname.length >= 4)
    }

    // Function to validate an email address.
    fun validateEmail(email: String): ValidationResult {
        // Return a ValidationResult with a status indicating whether the email is valid.
        return ValidationResult(!email.isNullOrEmpty())
    }

    // Function to validate a password.
    fun validatePassword(password: String): ValidationResult {
        // Return a ValidationResult with a status indicating whether the password is valid.
        return ValidationResult(!password.isNullOrEmpty() && password.length >= 4)
    }
}

// Define a data class named ValidationResult to represent the result of a validation check.
data class ValidationResult(
    // A boolean value indicating whether the validation check passed (true) or failed (false).
    val status: Boolean = false
)
