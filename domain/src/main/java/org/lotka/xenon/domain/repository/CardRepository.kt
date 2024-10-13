package org.lotka.xenon.domain.repository

import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.CardModel

interface CardRepository {
    suspend fun saveItemToCart(item: CardModel)
    fun getItemsInCart(): Flow<List<CardModel>>
    suspend fun removeItemFromCart(itemId: String)
}