package org.lotka.xenon.data.remote.dao.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.lotka.xenon.domain.model.Category

@Entity(tableName = "category_table")
data class CategoryEntity(
    @PrimaryKey val id: Int? = null,
    val picUrl: String? = null,
    val title: String? = null
)

fun CategoryEntity.toCategory() = Category(
    id = id,
    picUrl = picUrl,
    title = title
)

fun Category.toCategoryEntity()= CategoryEntity(
    id = id,
    picUrl = picUrl,
    title = title
)
