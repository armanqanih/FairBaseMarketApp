package org.lotka.xenon.presentation.screen.edit_profile

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
import org.lotka.xenon.domain.usecase.profile.GetProfileUseCase
import org.lotka.xenon.domain.usecase.profile.UpdateProfileUseCase
import org.lotka.xenon.domain.util.Resource
import org.lotka.xenon.presentation.util.PasswordTextFieldState
import org.lotka.xenon.presentation.util.StandardTextFieldState
import org.lotka.xenon.presentation.util.UiEvent

import javax.inject.Inject
@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(EditProfileState())
    val state = _state.asStateFlow()

    private val _passwordState = MutableStateFlow(PasswordTextFieldState())
    val passwordState = _passwordState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("userId")?.let { userId ->
            getProfile(userId)
        }
    }

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.UserNameChange -> {
                _state.value = _state.value.copy(userNameState = event.userNameState)
            }

            is EditProfileEvent.EmailChange -> {
                _state.value = _state.value.copy(emailState = event.email)
            }
            is EditProfileEvent.PasswordChange -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.password.text
                )
            }
            is EditProfileEvent.CropProfilePicture -> {
                _state.value = _state.value.copy(profileImageUri = event.uri)
            }
            EditProfileEvent.UpdateProfile -> {
                updateProfile()
            }
            EditProfileEvent.IsPasswordVisibility -> {
                _passwordState.value = _passwordState.value.copy(
                    isPasswordVisible = !passwordState.value.isPasswordVisible
                )
            }
        }
    }

    private fun updateProfile() {
        viewModelScope.launch {
            val user = _state.value.user
            if (user == null) {
                _eventFlow.emit(UiEvent.ShowSnakeBar("User data is missing."))
                return@launch
            }

            _state.value = _state.value.copy(isLoading = true)

            updateProfileUseCase(
                updateProfileData = User(
                    userId = user.userId,
                    username = _state.value.userNameState,
                    email = _state.value.emailState,
                ),
                profileImageUri = _state.value.profileImageUri
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(isLoading = false)
                        _eventFlow.emit(UiEvent.ShowSnakeBar("Profile Updated Successfully"))
                        _eventFlow.emit(UiEvent.onNavigateUp)
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = result.message,
                            isLoading = false)
                        _eventFlow.emit(UiEvent.ShowSnakeBar(result.message ?: "Unknown error"))
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private fun getProfile(userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getProfileUseCase(userId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val user = result.data ?: run {
                            _eventFlow.emit(UiEvent.ShowSnakeBar("Profile Not Found"))
                            return@collect
                        }
                        _state.value = _state.value.copy(
                            user = user,
                            userNameState =  user.username,
                            emailState =  user.email?:"",
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false)
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
