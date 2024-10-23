package org.lotka.xenon.domain.util

import org.lotka.xenon.domain.model.User

data class SignInResult(
    val data: User?,
    val errorMessage: String?

)

sealed interface CredentialSignInResult {
    data class Success(val username: String): CredentialSignInResult
    data object Cancelled: CredentialSignInResult, CredentialSignUpResult
    data object Failure: CredentialSignInResult, CredentialSignUpResult
    data object NoCredentials: CredentialSignInResult, CredentialSignUpResult
}

sealed interface CredentialSignUpResult {
    data class Success(val username: String): CredentialSignUpResult
    data object Cancelled: CredentialSignUpResult, CredentialSignInResult
    data object Failure: CredentialSignUpResult, CredentialSignInResult
}