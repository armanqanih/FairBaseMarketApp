package org.lotka.xenon.domain.util.error

sealed class AuthError : Error() {
    object FieldEmpty :  AuthError()
    object InputTooShort :  AuthError()
    object InvalidEmail : AuthError()
    object InvalidPassword : AuthError()
}