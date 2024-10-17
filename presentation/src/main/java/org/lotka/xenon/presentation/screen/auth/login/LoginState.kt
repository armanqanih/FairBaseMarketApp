package org.lotka.xenon.presentation.screen.auth.login

data class LoginState (
    val isLoading: Boolean = false,
    val error: String? = null
)