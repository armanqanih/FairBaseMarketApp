package org.lotka.xenon.presentation.screen.auth.login

import com.google.firebase.auth.AuthResult
import org.lotka.xenon.domain.model.User

data class LoginState (
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)