package org.lotka.xenon.data.remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.data.remote.dao.entity.ItemsEntity

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

//    For Add And Remove From My Card Screen

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItemToCart(item: ItemsEntity)

    @Query("SELECT * FROM items_table WHERE isInCart = 1")
    fun getItemToCard(): Flow<List<ItemsEntity>>

    @Query("DELETE FROM items_table WHERE categoryId = :itemId")
    suspend fun removeItemFromCart(itemId: String)


}