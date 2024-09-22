package org.lotka.xenonx.data.remote.dto

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class ArticleDto(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val sourceDto: SourceDto,
    val title: String,
    @PrimaryKey val url: String,
    val urlToImage: String
):Parcelable