package org.lotka.xenon.data.remote.dao.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.lotka.xenon.data.util.Constants.ITEM_TABLE
import org.lotka.xenon.domain.model.Item


@Entity(tableName = ITEM_TABLE)
data class ItemsEntity(
    @PrimaryKey val categoryId: Int? = null,
    val description: String? = null,
    val model: List<String>? = null,
    val picUrl: List<String>? = null,
    val price: Double? = null,
    val rating: Double? = null,
    val showRecommended: Boolean? = null,
    val title: String? = null,

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

)
