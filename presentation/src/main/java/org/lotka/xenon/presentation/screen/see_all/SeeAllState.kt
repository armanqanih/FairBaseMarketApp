package org.lotka.xenon.presentation.screen.see_all

import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.model.Items

data class SeeAllState(
    val categories: List<Category> = emptyList(),
    val itemsList: List<Items> = emptyList(),
    val category: Category? = null,
    val items: Items? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
