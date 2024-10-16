package org.lotka.xenon.data.remote.dao.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.lotka.xenon.data.remote.dao.local.ProfileDao
import org.lotka.xenon.data.remote.dao.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class ProfileDataBase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao

}