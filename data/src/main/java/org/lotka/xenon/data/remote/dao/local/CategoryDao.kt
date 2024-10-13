package org.lotka.xenon.data.remote.dao.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.data.remote.dao.local.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategories(categories: List<CategoryEntity>)

    @Query("SELECT * FROM CATEGORY_TABLE")
    fun getAllCategories(): Flow<List<CategoryEntity>>
}
