package org.lotka.xenon.presentation.screen.explore

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
import org.lotka.xenon.domain.usecase.explorer.GetCategoriesUseCase
import org.lotka.xenon.domain.usecase.explorer.GetHomeItemListUseCase
import org.lotka.xenon.domain.usecase.wish_list.RemoveItemFromWishListUseCase
import org.lotka.xenon.domain.usecase.wish_list.SaveItemToWishListUseCase
import org.lotka.xenon.domain.util.Resource
import org.lotka.xenon.presentation.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getHomeItemListUseCase: GetHomeItemListUseCase,
    private val saveItemToWishListUseCase: SaveItemToWishListUseCase,
    private val removeItemFromWishListUseCase: RemoveItemFromWishListUseCase,
) : ViewModel() {


    private val _state = MutableStateFlow(ExploreState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getCategories()
        getItems()
    }






    fun onEvent(event: ExploreEvent, item: WishListModel?) {
        when (event) {
            ExploreEvent.onFavoreateIconClick -> {
                item?.let { toggleFavorite(it) }  // Ensure it toggles the correct item's favorite status
            }
        }
    }

    fun toggleFavorite(item: WishListModel) {
        viewModelScope.launch {
            try {
                val isInWishlist = _state.value.itemWishList.any { it.categoryId == item.categoryId }
                val updatedItem = item.copy(isFavorite = !isInWishlist)
                if (!isInWishlist) {
                    saveItemToWishList(updatedItem)

//                    Log.d("WishList", "Item saved: ${updatedItem.title}")
                } else {
                    removeItemFromWishList(updatedItem.categoryId)
//
//                    Log.d("WishList", "Item removed: ${updatedItem.title}")
                }

                val updatedWishList = _state.value.itemWishList.toMutableList().apply {
                    val index = indexOfFirst { it.categoryId == item.categoryId }
                    if (index != -1) {
                        removeAt(index)
                    } else {
                        add(updatedItem)
                    }
                }
                _state.value = _state.value.copy(itemWishList = updatedWishList)

            } catch (e: Exception) {
                Log.e("ExploreViewModel", "Error toggling favorite: ${e.message}")
            }
        }
    }



    private fun saveItemToWishList(item: WishListModel) {
        viewModelScope.launch {
                saveItemToWishListUseCase.invoke(item)
        }
    }

    fun removeItemFromWishList(itemId:String){
        viewModelScope.launch {
            removeItemFromWishListUseCase.invoke(itemId)
        }
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
            getHomeItemListUseCase().collect { result ->
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