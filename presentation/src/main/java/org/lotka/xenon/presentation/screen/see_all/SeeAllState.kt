package org.lotka.xenon.presentation.screen.see_all

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.model.Items

data class SeeAllState(
    val categories: List<Category> = emptyList(),
    var itemsList: Flow<PagingData<Items>> = emptyFlow(),
    val category: Category? = null,
    val items: Items? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
