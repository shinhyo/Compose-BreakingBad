package io.github.shinhyo.brba.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.shinhyo.brba.data.local.AppDatabase.Companion.DB_VERSION

@Database(
    entities = [
        CharacterEntity::class
    ],
    version = DB_VERSION,
    exportSchema = false
)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_VERSION = 1
        const val NAME = "app.db"
    }

    abstract fun characterDao(): CharacterDao
}