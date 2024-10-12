package org.lotka.xenon.presentation.screen.explore

import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.model.Item
import org.lotka.xenon.domain.model.WishListModel

data class ExploreState(
    val categories: List<Category> = emptyList(),
    var itemList: List<Item> = emptyList(),
    var itemWishList: List<WishListModel> = emptyList(),
    val category: Category? = null,
    val item: Item? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isFavorite: Boolean = false,
    val isInWishList: Boolean = false,

)
