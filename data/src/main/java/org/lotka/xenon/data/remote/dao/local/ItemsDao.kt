package org.lotka.xenon.data.remote.dao.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.data.remote.dao.local.entity.CartEntity
import org.lotka.xenon.data.remote.dao.local.entity.ItemsEntity
import org.lotka.xenon.data.remote.dao.local.entity.WishListEntity

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



    //    for Search
    @Query("SELECT * FROM items_table WHERE title LIKE :query")
    fun searchItemsInRoom(query: String): Flow<List<ItemsEntity>>


}