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
    val isOptionRevealed: Boolean = false,
    var isFavorite: Boolean = false,
    val isInCart: Boolean = true,
    val isInWishList: Boolean = false
)

fun Item.toWishListModel(): WishListModel {
    return WishListModel(
        categoryId = (this.categoryId ?: 0).toString(),  // Provide default if null
        title = this.title ?: "Unknown",    // Handle nullable fields safely
        price = this.price ?: 0.0,
        picUrl = this.picUrl?.firstOrNull() ?: "", // Get first image URL or empty string
        rating = this.rating,
        isFavorite = this.isFavorite
    )
}

fun Item.toCardModel(): CardModel {
    return CardModel(
        categoryId = (this.categoryId ?: 0).toString(),  // Provide default if null
        title = this.title ?: "Unknown",    // Handle nullable fields safely
        price = this.price ?: 0.0,
        picUrl = this.picUrl?.firstOrNull() ?: "", // Get first image URL or empty string
        rating = this.rating,
    )
}

