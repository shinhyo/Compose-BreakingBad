/*
 * Copyright 2021 shinhyo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.shinhyo.brba.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.shinhyo.brba.core.database.AppDatabase.Companion.DB_VERSION
import io.github.shinhyo.brba.core.database.dao.CharacterDao
import io.github.shinhyo.brba.core.database.model.CharacterEntity
import io.github.shinhyo.brba.core.database.util.DateTypeConverter

@Database(
    entities = [
        CharacterEntity::class,
    ],
    version = DB_VERSION,
    exportSchema = false,
)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_VERSION = 1
        const val NAME = "app.db"
    }

    abstract fun characterDao(): CharacterDao
}