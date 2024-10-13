package org.lotka.xenon.data.remote.dao.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.lotka.xenon.data.remote.converters.Converters
import org.lotka.xenon.data.remote.dao.local.CategoryDao
import org.lotka.xenon.data.remote.dao.local.ItemsDao
import org.lotka.xenon.data.remote.dao.local.entity.CartEntity
import org.lotka.xenon.data.remote.dao.local.entity.CategoryEntity
import org.lotka.xenon.data.remote.dao.local.entity.ItemsEntity
import org.lotka.xenon.data.remote.dao.local.entity.WishListEntity

@Database(
    entities = [CategoryEntity::class, ItemsEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun itemsDao(): ItemsDao

//    companion object {
//        val MIGRATION_2_3 = object : Migration(2, 3) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                // Create cart_table if it doesn't exist
//                database.execSQL(
//                    """
//            CREATE TABLE IF NOT EXISTS `cart_table` (
//                `categoryId` TEXT NOT NULL PRIMARY KEY,
//                `title` TEXT NOT NULL,
//                `price` REAL NOT NULL,
//                `picUrl` TEXT NOT NULL,
//                `rating` REAL
//            )
//        """.trimIndent()
//                )
//
//                // Create wishlist_table if it doesn't exist
//                database.execSQL(
//                    """
//            CREATE TABLE IF NOT EXISTS `wishlist_table` (
//                `categoryId` TEXT NOT NULL PRIMARY KEY,
//                `title` TEXT NOT NULL,
//                `price` REAL NOT NULL,
//                `picUrl` TEXT NOT NULL,
//                `rating` REAL
//            )
//        """.trimIndent()
//                )
//            }
//        }
//
//
//    }


}
