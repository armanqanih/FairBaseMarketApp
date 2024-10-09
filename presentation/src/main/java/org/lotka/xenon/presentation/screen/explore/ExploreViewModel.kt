package org.lotka.xenon.presentation.screen.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenon.domain.usecase.GetCategoriesUseCase
import org.lotka.xenon.domain.usecase.GetItemListUseCase
import org.lotka.xenon.domain.util.Resource
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getItemListUseCase: GetItemListUseCase
) : ViewModel() {





    private val _state = MutableStateFlow(ExploreState())
    val state = _state.asStateFlow()

    init {
        getCategories()
        getItems()
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            categories = result.data ?: emptyList(),
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = result.message ?: "An unexpected error occurred",
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = false)
                    }
                }
            }
        }

    }

    private fun getItems() {
        viewModelScope.launch {
            getItemListUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            itemList = result.data ?: emptyList(),
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = result.message ?: "An unexpected error occurred",
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = false)
                    }
                }
            }
        }

    }

}