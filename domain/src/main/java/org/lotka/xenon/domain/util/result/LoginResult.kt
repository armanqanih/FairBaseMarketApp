package org.lotka.xenon.domain.util.result

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.util.Resource

import org.lotka.xenon.domain.util.error.AuthError

data class LoginResult  (
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result : Flow<Resource<AuthResult>>? = null

)