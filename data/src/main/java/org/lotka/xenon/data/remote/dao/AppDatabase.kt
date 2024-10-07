package org.lotka.xenon.data.remote.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.lotka.xenon.data.remote.converters.Converters
import org.lotka.xenon.data.remote.dao.entity.CategoryEntity
import org.lotka.xenon.data.remote.dao.entity.ItemsEntity

@Database(
    entities = [CategoryEntity::class, ItemsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun itemsDao(): ItemsDao
}
