package org.lotka.xenon.data.remote.dao.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.data.remote.dao.local.entity.CartEntity

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItemToCart(item: CartEntity)

    @Query("SELECT * FROM CARD_TABLE")
    fun getItemsInCart(): Flow<List<CartEntity>>

    @Query("DELETE FROM CARD_TABLE WHERE categoryId = :itemId")
    suspend fun removeItemFromCart(itemId: String)
}