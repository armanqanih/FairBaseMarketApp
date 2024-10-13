package org.lotka.xenon.data.remote.dao.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.data.remote.dao.local.entity.WishListEntity

@Dao
interface WishListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItemToWishList(item: WishListEntity)

    @Query("SELECT * FROM WISHLIST_TABLE")
    fun getItemsInWishList(): Flow<List<WishListEntity>>


    @Query("DELETE FROM WISHLIST_TABLE WHERE categoryId = :itemId")
    suspend fun removeItemFromWishList(itemId: String)

}