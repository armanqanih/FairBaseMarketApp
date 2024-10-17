package org.lotka.xenon.presentation.util

import org.lotka.xenon.domain.util.error.Error

data class PasswordTextFieldState(
    val text : String = "",
    val error: Error? = null,
    val isPasswordVisible : Boolean = false
)
