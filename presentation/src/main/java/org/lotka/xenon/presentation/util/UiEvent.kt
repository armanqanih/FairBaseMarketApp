package org.lotka.xenon.presentation.util

sealed class UiEvent {

    data class ShowSnakeBar (val message : String) : UiEvent()
    object onNavigateUp :UiEvent()

}