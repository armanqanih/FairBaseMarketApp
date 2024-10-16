package org.lotka.xenon.presentation.screen.profile



import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenon.domain.usecase.profile.GetProfileUseCase
import org.lotka.xenon.domain.util.Resource
import org.lotka.xenon.presentation.util.StandardTextFieldState
import org.lotka.xenon.presentation.util.UiEvent

import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _toolbarState = MutableStateFlow(ProfileToolbarState())
    val toolbarState = _toolbarState.asStateFlow()

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {

        savedStateHandle.get<String>("userId")?.let { userId ->
            getProfile(userId)
        }
    }




    // Fetches the profile from the server using the use case
    private fun getProfile(userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)  // Show loading state
            getProfileUseCase(userId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val user = result.data
                        if (user != null) {
                            _state.value = _state.value.copy(user = user, isLoading = false)
                        } else {
                            _eventFlow.emit(UiEvent.ShowSnakeBar("Profile Not Found"))
                            _state.value = _state.value.copy(isLoading = false)
                        }
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(UiEvent.ShowSnakeBar(result.message ?: "Unknown error"))
                        _state.value = _state.value.copy(isLoading = false)
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}








