package org.lotka.xenon.presentation.screen.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenon.domain.model.CardModel
import org.lotka.xenon.domain.model.Item
import org.lotka.xenon.domain.model.toCardModel
import org.lotka.xenon.domain.usecase.detail.GetDetailItemUseCase
import org.lotka.xenon.domain.usecase.card.SaveItemToCartUseCase
import org.lotka.xenon.domain.util.Resource
import org.lotka.xenon.presentation.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class DetailViewModelViewModel @Inject constructor(
    private val getDetailItemUseCase: GetDetailItemUseCase,
    private val saveItemToCartUseCase: SaveItemToCartUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("itemId")?.let { itemId ->
            getDetailItem(itemId)
        }
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            DetailEvent.BuyNow -> {
                saveItemToCart(_state.value.item?.toCardModel())
            }
        }
    }

    private fun saveItemToCart(item: CardModel?) {
        viewModelScope.launch {
            item?.let {

                try {
                    saveItemToCartUseCase.invoke(it)

                    _eventFlow.emit(UiEvent.ShowSnakeBar("Added to Cart List Successfully"))
                    Log.d("DetailViewModel", "Item saved: ${it.title}")
                } catch (e: Exception) {
                    Log.e("DetailViewModel", "Error saving item to cart: ${e.message}")
                    _eventFlow.emit(UiEvent.ShowSnakeBar("Error adding to Cart List"))

                }
            } ?: run {
                Log.e("DetailViewModel", "Item is null, cannot save to cart.")
                _eventFlow.emit(UiEvent.ShowSnakeBar("Item not found"))
            }
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