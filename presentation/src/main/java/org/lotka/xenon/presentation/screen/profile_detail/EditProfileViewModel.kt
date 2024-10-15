package org.lotka.xenonx.presentation.screen.edit_profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenon.presentation.screen.profile_detail.EditProfileEvent
import org.lotka.xenon.presentation.screen.profile_detail.EditProfileState
import org.lotka.xenon.presentation.util.PasswordTextFieldState
import org.lotka.xenon.presentation.util.UiEvent

import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(

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

            is EditProfileEvent.FamilyNameChange -> {
                _state.value = _state.value.copy(familyNameState = event.familyName)
            }

            is EditProfileEvent.EmailChange -> {
                _state.value = _state.value.copy(emailState = event.email)
            }

            is EditProfileEvent.PasswordChange -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.password.text)
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

//        viewModelScope.launch {
//            _state.value = _state.value.copy(isLoading = true)
//
//            profileUseCases.updateProfile.invoke(
//                updateProfileData = UserModel(
//                    userId = _state.value.profile?.userId ?: "",
//                    username = _state.value.userNameState.text,
//                    profileImageUrl = _state.value.profile?.profileImageUrl ?: "",
//                    bannerUrl = _state.value.profile?.bannerUrl ?: "",
//                    bio = _state.value.bioState.text,
//                    skills = _state.value.selectedSkills,
//                    githubUrl = _state.value.githubTextState.text,
//                    linkedInUrl = _state.value.linkedInTextState.text,
//
//                    ), bannerImageUri = _state.value.bannerImageUri,
//                profileImageUri = _state.value.profileImageUri
//            ).collect { result ->
//                when (result) {
//                    is Resource.Success -> {
//                        _state.value = _state.value.copy(isLoading = false)
//                        _eventFlow.emit(UiEvent.ShowSnakeBar("Profile Updated Successfully"))
//
//                        _eventFlow.emit(UiEvent.NavigateUp)
//
//                    }
//                    is Resource.Error -> {
//                        _state.value = _state.value.copy(isLoading = false)
//                        _eventFlow.emit(UiEvent.ShowSnakeBar(result.message ?: "Unknown error"))
//                    }
//                    is Resource.Loading -> {
//                        _state.value = _state.value.copy(isLoading = true)
//                    }
//                }}
//        }


    }


    private fun getProfile(userId: String) {
        viewModelScope.launch {

            _state.value = _state.value.copy(isLoading = true)

//            profileUseCases.getProfile.invoke(userId).collect {result->
//                when(result){
//                    is Resource.Success -> {
//                        val profile = result.data?: kotlin.run {
//                            _eventFlow.emit(UiEvent.ShowSnakeBar("Profile Not Found"))
//                            null
//                        }
//                        _state.value = _state.value.copy(
//                            userNameState = StandardTextFieldState(text = profile?.username ?: ""),
//                            githubTextState = StandardTextFieldState(text = profile?.githubUrl ?: ""),
//                            linkedInTextState = StandardTextFieldState(text = profile?.linkedInUrl ?: ""),
//                            bioState = StandardTextFieldState(text = profile?.bio ?: ""),
//                            selectedSkills = profile?.skills ?: emptyList(),
//                            profile = profile,
//                            isLoading = false
//                        )
//                    }
//                    is Resource.Error -> {
//                        _state.value = _state.value.copy(isLoading = false)
//                        _eventFlow.emit(UiEvent.ShowSnakeBar(result.message ?: "Unknown error"))
//                    }
//                    is Resource.Loading -> {
//                        _state.value = _state.value.copy(isLoading = true)
//                    }
//
//                }
//            }
        }
    }


}