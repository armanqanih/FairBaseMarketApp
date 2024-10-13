package org.lotka.xenon.data.remote.dao.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.lotka.xenon.data.remote.dao.local.CardDao
import org.lotka.xenon.data.remote.dao.local.entity.CartEntity

@Database(entities = [CartEntity::class], version = 1, exportSchema = false)
abstract class CardDatabase : RoomDatabase() {
    abstract fun cartDao(): CardDao

}
