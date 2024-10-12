package org.lotka.xenon.presentation.screen.detail

import org.lotka.xenon.domain.model.CardModel
import org.lotka.xenon.domain.model.Item

data class DetailState(
    val item: Item? = null,
    val itemCard: CardModel? = null,
    val isSelectModel: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isInCard: Boolean = false
)
