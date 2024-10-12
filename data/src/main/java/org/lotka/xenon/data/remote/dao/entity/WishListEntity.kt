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

fun WishListEntity.toWishListModel()= WishListModel(
    categoryId = categoryId,
    title = title,
    price = price,
    picUrl = picUrl,
    rating = rating

)

fun WishListModel.toWishListEntity()= WishListEntity(
    categoryId = categoryId,
    title = title,
    price = price,
    picUrl = picUrl,
    rating = rating

)