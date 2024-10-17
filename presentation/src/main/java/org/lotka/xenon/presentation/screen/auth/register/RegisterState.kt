package org.lotka.xenon.presentation.screen.auth.register

data class RegisterState (
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError : String? = ""
)