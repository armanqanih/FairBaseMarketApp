package org.lotka.xenon.data.remote.dao.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.lotka.xenon.data.util.Constants.CARD_TABLE
import org.lotka.xenon.domain.model.CardModel

@Entity(tableName = CARD_TABLE)
data class CartEntity(
    @PrimaryKey val categoryId: String,
    val title: String,
    val price: Double,
    val picUrl: String,
    val rating: Double? = null,
    val isOptionRevealed: Boolean = false,
)
fun CartEntity.toCardModel()= CardModel(
    categoryId = categoryId,
    title = title,
    price = price,
    picUrl = picUrl,
    rating = rating,
    isOptionRevealed = isOptionRevealed

)
fun CardModel.toCartEntity()= CartEntity(
    categoryId = categoryId,
    title = title,
    price = price,
    picUrl = picUrl,
    rating = rating,
    isOptionRevealed= isOptionRevealed

)

