package org.lotka.xenon.presentation.screen.wish_list


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenon.domain.model.WishListModel

import org.lotka.xenon.domain.usecase.card.RemoveItemFromCartUseCase
import org.lotka.xenon.domain.usecase.wish_list.GetItemsInWishListUseCase
import org.lotka.xenon.domain.usecase.wish_list.RemoveItemFromWishListUseCase

import org.lotka.xenon.presentation.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val getItemInWishList: GetItemsInWishListUseCase,
    private val removeItemFromWishListUseCase: RemoveItemFromWishListUseCase
):ViewModel() {

    private val _state = MutableStateFlow(WishListState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getWishListItems()
    }

    private fun getWishListItems() {
        viewModelScope.launch {
            getItemInWishList.invoke().collect { itemsList ->
                val mappedItems = itemsList.map { item ->
                    WishListModel(
                        categoryId = item.categoryId,
                        title = item.title,
                        picUrl = item.picUrl,
                        rating = item.rating,
                        price = item.price
                    )
                }
                _state.value = _state.value.copy(wishListItem = mappedItems)
                Log.d("WishListViewModel", "Fetched items: $mappedItems")
            }
        }
    }




    fun removeItemFromWishList(itemId: String) {
        viewModelScope.launch {
            try {
                removeItemFromWishListUseCase.invoke(itemId)
                Log.d("WishListViewModel", "Item removed from wishlist: $itemId")
            } catch (e: Exception) {
                Log.e("WishListViewModel", "Error removing item from wishlist: ${e.message}")
            }
        }
    }
}