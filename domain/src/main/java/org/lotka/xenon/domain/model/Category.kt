package org.lotka.xenon.domain.model

data class Category(
    val id: Int? = null,      // Nullable to avoid issues with missing values
    val picUrl: String? = null,
    val title: String? = null
)
