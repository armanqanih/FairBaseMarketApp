package org.lotka.xenon.presentation.screen.detail

import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.model.Items

data class DetailState(

    val item: Items? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
