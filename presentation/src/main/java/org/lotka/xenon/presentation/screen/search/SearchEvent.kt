package org.lotka.xenon.presentation.screen.search

sealed class SearchEvent {
    data class UpdateSearchQuery(val query: String) : SearchEvent()
    object SearchMovies : SearchEvent()


}