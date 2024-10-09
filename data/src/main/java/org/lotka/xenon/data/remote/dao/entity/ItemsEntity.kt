package org.lotka.xenon.data.remote.dao.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.lotka.xenon.domain.model.Item


@Entity(tableName = "items_table")
data class ItemsEntity(
    @PrimaryKey val categoryId: Int? = null,
    val description: String? = null,
    val model: List<String>? = null, // List<String> that needs conversion
    val picUrl: List<String>? = null, // List<String> that needs conversion
    val price: Double? = null,
    val rating: Double? = null,
    val showRecommended: Boolean? = null,
    val title: String? = null,
    val isInCart: Boolean = true
)


fun ItemsEntity.toItems() = Item(
    categoryId = categoryId,
    description = description,
    model = model,
    picUrl = picUrl,
    price = price,
    rating = rating,
    showRecommended = showRecommended,
    title = title,
    isInCart = isInCart
)

fun Item.toItemsEntity() = ItemsEntity(
    categoryId = categoryId,
    description = description,
    model = model,
    picUrl = picUrl,
    price = price,
    rating = rating,
    showRecommended = showRecommended,
    title = title,
    isInCart = isInCart
)
