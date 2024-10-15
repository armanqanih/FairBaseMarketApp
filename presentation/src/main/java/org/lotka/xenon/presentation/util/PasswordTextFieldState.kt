package org.lotka.xenon.presentation.util

data class PasswordTextFieldState(
    val text : String = "",
    val error: Error? = null,
    val isPasswordVisible : Boolean = false
)
