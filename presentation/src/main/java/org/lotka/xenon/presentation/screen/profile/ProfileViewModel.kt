package org.lotka.xenon.presentation.screen.profile

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenon.domain.model.User
import org.lotka.xenon.domain.usecase.auth.LogOutUserUseCase
import org.lotka.xenon.domain.usecase.profile.GetProfileUseCase
import org.lotka.xenon.domain.util.Resource
import org.lotka.xenon.presentation.ui.navigation.ScreensNavigation
import org.lotka.xenon.presentation.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val logOutUserUseCase: LogOutUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {



    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("userId")?.let { userId ->
            getProfile(userId)
        }
    }


    fun logoutUser() {
        viewModelScope.launch {
            Log.d("Logout", "logoutUser function called")
            logOutUserUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        Log.e("Logout Error", result.message ?: "Unknown Error")
                        _eventFlow.emit(UiEvent.ShowSnakeBar("Can't logout, please try again later"))
                        _state.value = _state.value.copy(
                            error = "Can't logout, please try again later",
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        Log.d("Logout", "User successfully logged out")
                        _state.value = _state.value.copy(isLoading = false)
                        _eventFlow.emit(UiEvent.Navigate(ScreensNavigation.LoginScreen.route)) // Navigate to Login screen
                    }
                }
            }
        }
    }



    // Fetches the profile using the GetProfileUseCase
    fun getProfile(userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true) // Set loading state

            getProfileUseCase(userId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val user = result.data
                        if (user != null) {
                            _state.value = _state.value.copy(
                                user = User(
                                    userId = user.userId,
                                    username = user.username,
                                    profileImageUrl = user.profileImageUrl
                                ),
                                isLoading = false
                            )
                        } else {
                            _state.value = _state.value.copy(
                                error = "Profile not found",
                                isLoading = false
                            )
                            _eventFlow.emit(UiEvent.ShowSnakeBar("Profile not found"))
                        }
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = result.message ?: "Unknown error",
                            isLoading = false
                        )
                        _eventFlow.emit(UiEvent.ShowSnakeBar(result.message ?: "Unknown error"))
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }




}
