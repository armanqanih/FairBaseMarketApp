package org.lotka.xenon.data.remote.repository

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.lotka.xenon.data.remote.dao.local.CardDao
import org.lotka.xenon.data.remote.dao.local.database.CardDatabase
import org.lotka.xenon.data.remote.dao.local.entity.toCardModel
import org.lotka.xenon.data.remote.dao.local.entity.toCartEntity
import org.lotka.xenon.domain.model.CardModel
import org.lotka.xenon.domain.repository.CardRepository
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val db: CardDatabase,

    ) : CardRepository {
    override suspend fun saveItemToCart(item: CardModel) {
        db.cartDao().saveItemToCart(item.toCartEntity())
    }


    override fun getItemsInCart(): Flow<List<CardModel>> {
        return db.cartDao().getItemsInCart().map { itemsEntityList ->
            itemsEntityList.map { entity -> entity.toCardModel() }
        }
    }


    override suspend fun removeItemFromCart(itemId: String) {
        db.cartDao().removeItemFromCart(itemId)
    }


}