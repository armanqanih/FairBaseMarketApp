package org.lotka.xenon.data.remote.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Create the new table
        database.execSQL("""
            CREATE TABLE new_items_table (
                id INTEGER PRIMARY KEY NOT NULL,
                name TEXT,
                price REAL
            )
        """.trimIndent())

        // Copy the data from the old table to the new table
        database.execSQL("""
            INSERT INTO new_items_table (id, name)
            SELECT id, name FROM items_table
        """)

        // Remove the old table
        database.execSQL("DROP TABLE items_table")

        // Rename the new table to the old table's name
        database.execSQL("ALTER TABLE new_items_table RENAME TO items_table")
    }
}
