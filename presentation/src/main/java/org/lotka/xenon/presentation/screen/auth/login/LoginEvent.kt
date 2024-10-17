package org.lotka.xenon.presentation.screen.auth.login

sealed class LoginEvent {

     data class EnterEmail (val userName:String): LoginEvent()
     data class EnterPassword (val password:String): LoginEvent()
     object IsPasswordVisibility : LoginEvent()
     object Login : LoginEvent()
     data class ShowSnakeBar(val message:String): LoginEvent()

}