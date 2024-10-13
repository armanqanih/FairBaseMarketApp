package org.lotka.xenon.data.remote.repository

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.lotka.xenon.data.remote.dao.local.CardDao
import org.lotka.xenon.data.remote.dao.local.WishListDao
import org.lotka.xenon.data.remote.dao.local.database.WishListDatabase
import org.lotka.xenon.data.remote.dao.local.entity.toWishListEntity
import org.lotka.xenon.data.remote.dao.local.entity.toWishListModel
import org.lotka.xenon.domain.model.WishListModel
import org.lotka.xenon.domain.repository.CardRepository
import org.lotka.xenon.domain.repository.WishListRepository
import javax.inject.Inject

class WishListRepositoryImpl @Inject constructor(
    private val db: WishListDatabase,

    ) : WishListRepository {
    override suspend fun saveItemToWishList(item: WishListModel) {
        db.wishListDao().saveItemToWishList(item.toWishListEntity())
    }

    override suspend fun removeItemFromWishList(itemId: String) {
        db.wishListDao().removeItemFromWishList(itemId)
    }


    override fun getItemsInWishList(): Flow<List<WishListModel>> {
        return   db.wishListDao().getItemsInWishList().map { itemsEntityList ->
            itemsEntityList.map { entity ->
                entity.toWishListModel() // Mapping from Entity to Model
            }
        }
    }

    }