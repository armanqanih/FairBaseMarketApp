package org.lotka.xenon.presentation.screen.search

import org.lotka.xenon.domain.model.Item

data class SearchState (
    val searchList:List<Item> = emptyList(),
    val isLoading: Boolean = false,
    var searchQuery: String = "",
    val error: String? = null,
    var searchActive : Boolean = false
)