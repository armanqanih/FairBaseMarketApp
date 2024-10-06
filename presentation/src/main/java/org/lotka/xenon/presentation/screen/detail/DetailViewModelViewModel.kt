package org.lotka.xenon.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenon.domain.usecase.GetCategoriesUseCase
import org.lotka.xenon.domain.usecase.GetDetailItemUseCase
import org.lotka.xenon.domain.usecase.GetItemListUseCase
import org.lotka.xenon.domain.util.Resource
import javax.inject.Inject

@HiltViewModel
class DetailViewModelViewModel @Inject constructor(
    private val getDetailItemUseCase: GetDetailItemUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("itemId")?.let { itemId->
        getDetailItem(itemId)
        }
    }



    fun getDetailItem(itemId: String) {
        viewModelScope.launch {
            getDetailItemUseCase(itemId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            item = result.data,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = result.message ?: "An unexpected error occurred",
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}