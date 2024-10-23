package org.lotka.xenon.presentation.screen.auth.login

import org.lotka.xenon.domain.util.CredentialSignInResult
import org.lotka.xenon.domain.util.CredentialSignUpResult
import org.lotka.xenon.domain.util.SignInResult

sealed interface LoginEvent {
     data class EnterEmail (val userName:String): LoginEvent
     data class EnterPassword (val password:String): LoginEvent
     data class ShowSnakeBar(val message:String): LoginEvent
     data class OnSignIn(val result: CredentialSignInResult): LoginEvent
     data class OnSignUp(val result: CredentialSignUpResult): LoginEvent
     object IsPasswordVisibility : LoginEvent
     object Login : LoginEvent

}