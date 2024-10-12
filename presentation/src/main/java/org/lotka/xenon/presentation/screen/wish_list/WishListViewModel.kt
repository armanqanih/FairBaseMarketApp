package org.lotka.xenon.presentation.screen.wish_list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import org.lotka.xenon.domain.usecase.card.RemoveItemFromCartUseCase
import org.lotka.xenon.domain.usecase.wish_list.GetItemsInWishListUseCase
import org.lotka.xenon.domain.usecase.wish_list.RemoveItemFromWishListUseCase

import org.lotka.xenon.presentation.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val getItemInWishList: GetItemsInWishListUseCase,
    private val removeItemFromCartUseCase: RemoveItemFromWishListUseCase
):ViewModel() {

    private val _state = MutableStateFlow(WishListState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getCardItem()
    }

    fun getCardItem() {
        viewModelScope.launch {
            getItemInWishList.invoke().collect { itemsList ->
                _state.value = _state.value.copy(
                    items = itemsList
                )
            }
        }
    }


    fun removeItemInCard(itemId: String) {
        viewModelScope.launch {
            removeItemFromCartUseCase.invoke(itemId)
        }
    }
}