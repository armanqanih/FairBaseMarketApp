package org.lotka.xenon.presentation.screen.profile

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


       fun logoutUser(){
           viewModelScope.launch {
               logOutUserUseCase.invoke().collect{result->
                   when(result){
                       is Resource.Error -> {
                           _eventFlow.emit(UiEvent.ShowSnakeBar("Cant Logout please try again later"))
                           _state.value = _state.value.copy(
                               error = "Cant Logout please try again later",
                               isLoading = false)
                       }
                       is Resource.Loading -> {
                           _state.value = _state.value.copy(isLoading = true)
                       }
                       is Resource.Success -> {
                           _eventFlow.emit(UiEvent.Navigate(ScreensNavigation.LoginScreen.route))
                       }
                   }
               }
           }

    }



    // Fetches the profile using the GetProfileUseCase
    private fun getProfile(userId: String) {
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
                                    email = user.email,
                                    username = user.username,
                                    profileImageUrl = user.profileImageUrl
                                ),
                                isLoading = false)
                        } else {
                            _eventFlow.emit(UiEvent.ShowSnakeBar("Profile not found"))
                            _state.value = _state.value.copy(
                                error = "Profile Not Found",
                                isLoading = false)
                        }
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(UiEvent.ShowSnakeBar(result.message ?: "Unknown error"))
                        _state.value = _state.value.copy(
                            error = result.message,
                            isLoading = false) // Reset loading state
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                }
            }
        }
    }



}
