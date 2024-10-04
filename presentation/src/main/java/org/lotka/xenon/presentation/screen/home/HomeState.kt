package org.lotka.xenon.presentation.screen.home

import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.model.Items

data class HomeState(
    val categories: List<Category> = emptyList(),
    val itemsList: List<Items> = emptyList(),
    val category: Category? = null,
    val items: Items? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
