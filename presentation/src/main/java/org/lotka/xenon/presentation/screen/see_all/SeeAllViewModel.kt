package org.lotka.xenon.presentation.screen.see_all

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenon.domain.usecase.GetItemByCategoryUseCase
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(
    private val getItemByCategoryUseCase: GetItemByCategoryUseCase,
    savedStateHandle: SavedStateHandle
):ViewModel(){


    val pc = getItemByCategoryUseCase("PC").cachedIn(viewModelScope)
    val smartphone = getItemByCategoryUseCase("SMARTPHONE").cachedIn(viewModelScope)
    val headphone = getItemByCategoryUseCase("HEADPHONE").cachedIn(viewModelScope)
    val console = getItemByCategoryUseCase("CONSOLE").cachedIn(viewModelScope)
    val camera = getItemByCategoryUseCase("CAMERA").cachedIn(viewModelScope)
    val smartwatch = getItemByCategoryUseCase("CAMERA").cachedIn(viewModelScope)

    private val _state = MutableStateFlow(SeeAllState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("categoryId")?.let { id ->
            getItemsByCategoryId(id)
        }

    }


    fun getItemsByCategoryId(categoryId: String) {
        viewModelScope.launch {
            val pagingDataFlow = getItemByCategoryUseCase(categoryId)
            _state.value = _state.value.copy(
                itemList = pagingDataFlow,
                isLoading = false,
                error = null
            )
        }
    }



}