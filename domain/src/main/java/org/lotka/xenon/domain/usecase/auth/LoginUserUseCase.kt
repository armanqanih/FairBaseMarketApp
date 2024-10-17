package org.lotka.xenon.domain.usecase.auth

import org.lotka.xenon.domain.repository.AuthRepository
import org.lotka.xenon.domain.util.error.AuthError
import org.lotka.xenon.domain.util.result.LoginResult

import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): LoginResult {

      val emailError = if (email.isEmpty()) AuthError.FieldEmpty else null
      val passwordError = if (password.isEmpty()) AuthError.FieldEmpty else null

        if (emailError != null || passwordError !=null ) {
            return LoginResult(
                emailError = emailError,
                passwordError = passwordError
            )
        }
        val result = repository.loginUser(email, password)
        return LoginResult(
          result = result
        )


    }
}