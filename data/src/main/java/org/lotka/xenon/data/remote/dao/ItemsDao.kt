package org.lotka.xenon.data.remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.data.remote.dao.entity.CartEntity
import org.lotka.xenon.data.remote.dao.entity.ItemsEntity
import org.lotka.xenon.data.remote.dao.entity.WishListEntity

@Dao
interface ItemsDao {

//    For Explore Screen
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHomeItems(items: List<ItemsEntity>)

    @Query("SELECT * FROM items_table WHERE categoryId = :categoryId")
    fun getItemsByCategory(categoryId: String): Flow<List<ItemsEntity>>

    @Query("SELECT * FROM items_table")
    fun getAllItems(): Flow<List<ItemsEntity>>

//For Detail Screen
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDetailItem(item: ItemsEntity)

    @Query("SELECT * FROM items_table WHERE categoryId = :itemId LIMIT 1")
    suspend fun getItemById(itemId: String): ItemsEntity?

//    For  My Card Screen

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItemToCart(item: CartEntity)

    @Query("SELECT * FROM cart_table")
    fun getItemsInCart(): Flow<List<CartEntity>>

    @Query("DELETE FROM cart_table WHERE categoryId = :itemId")
    suspend fun removeItemFromCart(itemId: String)

//    for WishList

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItemToWishList(item: WishListEntity)

    @Query("SELECT * FROM wishlist_table")
    fun getItemsInWishList(): Flow<List<WishListEntity>>

    @Query("DELETE FROM wishlist_table WHERE categoryId = :itemId")
    suspend fun removeItemFromWishList(itemId: String)


}