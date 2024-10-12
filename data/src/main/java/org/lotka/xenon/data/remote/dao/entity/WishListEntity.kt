package org.lotka.xenon.data.remote.dao.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.lotka.xenon.domain.model.CardModel
import org.lotka.xenon.domain.model.WishListModel

@Entity(tableName = "wishlist_table")
data class WishListEntity(
    @PrimaryKey val categoryId: String,
    val title: String,
    val price: Double,
    val picUrl: String,
    val rating: Double? = null
)

fun WishListEntity.toWishListModel(): WishListModel {
    return WishListModel(
        categoryId = this.categoryId,
        title = this.title,
        price = this.price,
        picUrl = this.picUrl,
        rating = this.rating
    )
}

fun WishListModel.toWishListEntity()= WishListEntity(
    categoryId = categoryId,
    title = title,
    price = price,
    picUrl = picUrl,
    rating = rating

)