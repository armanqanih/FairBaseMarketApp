package org.lotka.xenon.data.remote.dao.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.lotka.xenon.data.remote.dao.local.WishListDao
import org.lotka.xenon.data.remote.dao.local.entity.WishListEntity

@Database(entities = [WishListEntity::class], version = 1, exportSchema = false)
abstract class WishListDatabase : RoomDatabase() {
    abstract fun wishListDao(): WishListDao

}