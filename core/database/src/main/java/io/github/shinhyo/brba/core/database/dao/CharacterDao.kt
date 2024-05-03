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
package io.github.shinhyo.brba.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.shinhyo.brba.core.database.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: CharacterEntity): Long

    @Query(
        """
            SELECT * FROM ${CharacterEntity.TABLE_NAME}   
        """,
    )
    fun getAll(): Flow<List<CharacterEntity>>

    @Query(
        """
            SELECT * FROM ${CharacterEntity.TABLE_NAME} t
            WHERE t.charId=:charId
            ORDER BY t.ctime
        """,
    )
    fun getCharacter(charId: Long): Flow<CharacterEntity?>

    @Query(
        """
            SELECT * FROM ${CharacterEntity.TABLE_NAME} t
            WHERE t.favorite = 1
            ORDER BY CASE WHEN :isAsc = 1 THEN t.ctime END ASC,
            CASE WHEN :isAsc = 0 THEN t.ctime END DESC
        """,
    )
    fun getCharacter(isAsc: Boolean = true): Flow<List<CharacterEntity>>
}