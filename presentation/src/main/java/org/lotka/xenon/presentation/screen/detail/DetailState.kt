package org.lotka.xenon.presentation.screen.detail

import org.lotka.xenon.domain.model.Item

data class DetailState(
    val item: Item? = null,
    val isSelectModel: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
