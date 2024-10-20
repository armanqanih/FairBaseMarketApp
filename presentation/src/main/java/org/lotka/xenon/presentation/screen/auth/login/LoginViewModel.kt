package org.lotka.xenon.presentation.screen.auth.login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.lotka.xenon.domain.model.User
import org.lotka.xenon.domain.usecase.auth.GoogleSignInUseCase
import org.lotka.xenon.domain.usecase.auth.LoginUserUseCase
import org.lotka.xenon.domain.util.Resource
import org.lotka.xenon.domain.util.SignInResult
import org.lotka.xenon.presentation.ui.navigation.ScreensNavigation
import org.lotka.xenon.presentation.util.PasswordTextFieldState
import org.lotka.xenon.presentation.util.StandardTextFieldState
import org.lotka.xenon.presentation.util.TestTag.IS_LOGIN_PREFERENCES
import org.lotka.xenon.presentation.util.UiEvent


import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUserUseCase,
//    private val googleSignInUseCase: GoogleSignInUseCase,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _emailState = MutableStateFlow(StandardTextFieldState())
    val emailState = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow(PasswordTextFieldState())
    val passwordState = _passwordState.asStateFlow()

    fun saveLoginStatus(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(IS_LOGIN_PREFERENCES, isLoggedIn).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGIN_PREFERENCES, false)
    }


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnterPassword -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.password
                )
            }

            is LoginEvent.EnterEmail -> {
                _emailState.value = _emailState.value.copy(
                    text = event.userName
                )
            }

            is LoginEvent.ShowSnakeBar -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ShowSnakeBar(event.message))
                }
            }

            is LoginEvent.Login -> {
                viewModelScope.launch {
                    login()
                }
            }

            LoginEvent.IsPasswordVisibility -> {
                _passwordState.value = _passwordState.value.copy(
                    isPasswordVisible = !passwordState.value.isPasswordVisible
                )
            }
        }
    }


    private suspend fun login() {
        _emailState.value = _emailState.value.copy(error = null)
        _passwordState.value = _passwordState.value.copy(error = null)

        _state.value = _state.value.copy(isLoading = true)


        val email = emailState.value.text
        val password = passwordState.value.text

        // Call the use case to register the user
        val loginResult = loginUseCase(email, password)

        // Update the UI with potential errors

        if (loginResult.emailError != null) {
            _emailState.value = _emailState.value.copy(
                error = loginResult.emailError
            )
        }
        if (loginResult.passwordError != null) {
            _passwordState.value = _passwordState.value.copy(
                error = loginResult.passwordError
            )
        }

        _state.value = _state.value.copy(isLoading = true)

        try {
            // Collect the result from the use case
            loginUseCase(email, password).result?.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(isLoading = false)
                        result.data?.let {
                            // Emit an event for successful login
                            saveLoginStatus(true)
                            _eventFlow.emit(UiEvent.Navigate(ScreensNavigation.ExploreScreen.route))
                        }
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false)
                        // If error message contains "no user", show "You don't have an account" message
                        val errorMessage = if (result.message?.contains("no user") == true) {
                            "You don't have an account"
                        } else {
                            result.message ?: "An unexpected error occurred"
                        }
                        _eventFlow.emit(UiEvent.ShowSnakeBar(errorMessage))
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        } catch (e: Exception) {
            _state.value = _state.value.copy(isLoading = false)
            _eventFlow.emit(UiEvent.ShowSnakeBar("An internal error occurred: ${e.localizedMessage}"))
        }


    }

    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState() {
        _state.update { LoginState() }
    }
}






    // Function to handle Google Sign-In
    // Add logging to see if the function is triggered
//    fun googleSignIn(context: Context) {
//        Log.d("LoginViewModel", "Google sign-in initiated.")
//        viewModelScope.launch {
//            googleSignInUseCase.invoke(context).collect { result ->
//                when (result) {
//                    is Resource.Success -> {
//                        Log.d("LoginViewModel", "Sign-in successful.")
//                        _state.value = _state.value.copy(
//                            isLoading = false,
//                            user = User(
//                                result.data?.user?.uid ?: "",
//                                result.data?.user?.displayName ?: "",
//                                result.data?.user?.photoUrl.toString() ?: "",
//                                result.data?.user?.email ?: "",
//                            )
//                        )
//                        _eventFlow.emit(UiEvent.Navigate(ScreensNavigation.ProfileScreen.route))
//                    }
//                    is Resource.Error -> {
//                        Log.e("LoginViewModel", "Sign-in failed: ${result.message}")
//                        _state.value = _state.value.copy(
//                            isLoading = false,
//                            error = result.message
//                        )
//                    }
//                    is Resource.Loading -> {
//                        Log.d("LoginViewModel", "Signing in...")
//                        _state.value = _state.value.copy(isLoading = true)
//                    }
//                }
//            }
//        }
//    }










