package org.lotka.xenon.presentation.screen.home

import org.lotka.xenon.domain.model.Category

data class HomeState(
    val categories: List<Category> = emptyList(),
    val category: Category? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
