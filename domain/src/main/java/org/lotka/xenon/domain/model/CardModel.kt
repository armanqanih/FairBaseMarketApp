package org.lotka.xenon.domain.model


data class CardModel(
    val categoryId: String,
    val title: String,
    val price: Double,
    val picUrl: String,
    val rating: Double? = null,
    val isOptionRevealed: Boolean = false,
)


