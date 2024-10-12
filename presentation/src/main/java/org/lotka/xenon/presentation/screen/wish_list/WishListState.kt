package org.lotka.xenon.presentation.screen.wish_list

import org.lotka.xenon.domain.model.Item
import org.lotka.xenon.domain.model.WishListModel

data class WishListState(
    val wishListItem: List<WishListModel> = emptyList(),


)
