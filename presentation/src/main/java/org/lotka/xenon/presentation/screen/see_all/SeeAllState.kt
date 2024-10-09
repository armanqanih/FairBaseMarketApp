package org.lotka.xenon.presentation.screen.see_all

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.model.Item

data class SeeAllState(
    val categories: List<Category> = emptyList(),
    var itemList: Flow<PagingData<Item>> = emptyFlow(),
    val category: Category? = null,
    val item: Item? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
