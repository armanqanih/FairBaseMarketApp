package org.lotka.xenon.data.remote.dao.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.lotka.xenon.data.remote.dao.local.entity.ItemsEntity
import org.lotka.xenon.data.remote.dao.local.entity.UserEntity

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserData(user: UserEntity)

    @Query("SELECT * FROM USER_TABLE WHERE userId = :userId LIMIT 1")
    suspend fun getUserById(userId: String): UserEntity?
}