package org.lotka.xenon.domain.model

data class Items(
    val categoryId: Int? = null,               // Nullable Int
    val description: String? = null,            // Nullable String
    val model: List<String>? = null,            // Nullable List of Strings
    val picUrl: List<String>? = null,           // Nullable List of Strings
    val price: Double? = null,                  // Nullable Double
    val rating: Double? = null,                 // Nullable Double
    val showRecommended: Boolean? = null,       // Nullable Boolean
    val title: String? = null                    // Nullable String
)
