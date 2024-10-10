package org.lotka.xenon.domain.model


data class Item(
    val categoryId: Int? = null,
    val description: String? = null,
    val model: List<String>? = null,
    val picUrl: List<String>? = null,
    val price: Double? = null,
    val rating: Double? = null,
    val showRecommended: Boolean? = null,
    val title: String? = null,
    val isInCart: Boolean = true,
    val isOptionRevealed: Boolean = false
)
