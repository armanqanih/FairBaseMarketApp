package org.lotka.xenon.presentation.screen.explore

import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.model.Items

data class ExploreState(
    val categories: List<Category> = emptyList(),
    var itemsList: List<Items> = emptyList(),
    val category: Category? = null,
    val items: Items? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
