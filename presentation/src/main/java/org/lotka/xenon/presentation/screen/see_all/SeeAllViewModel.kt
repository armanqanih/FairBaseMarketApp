package org.lotka.xenon.presentation.screen.see_all

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenon.domain.usecase.GetItemByCategoryUseCase
import org.lotka.xenon.domain.util.Resource
import org.lotka.xenon.presentation.screen.home.HomeState
import org.lotka.xenon.presentation.screen.see_all.compose.SeeAllItem
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(
    private val getItemByCategoryUseCase: GetItemByCategoryUseCase,
    savedStateHandle: SavedStateHandle
):ViewModel(){

    private val _state = MutableStateFlow(SeeAllState())
    val state = _state.asStateFlow()

//    init {
//       savedStateHandle.get<Int>("categoryId")?.let { id->
//               getItemsByCategoryId(id)
//
//       }
//    }


      fun getItemsByCategoryId(categoryId: Int) {
        viewModelScope.launch {
            getItemByCategoryUseCase.invoke(categoryId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                       _state.value = _state.value.copy(
                        itemsList = result.data ?: emptyList()

                       )
                    }
                    is Resource.Error -> {
                        // Handle the error case here
                    }
                    is Resource.Loading -> {
                        // Handle loading state
                    }
                }
            }
        }
    }



}