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
package io.github.shinhyo.brba.data.local

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import io.github.shinhyo.brba.data.Character
import io.github.shinhyo.brba.data.local.CharacterEntity.Companion.TABLE_NAME
import java.util.*

@Immutable
@Entity(tableName = TABLE_NAME, indices = [(Index(value = ["charId"], unique = true))])
data class CharacterEntity(
    @PrimaryKey
    val charId: Long,
    val name: String,
    val img: String = "",
    val nickname: String,
    val favorite: Boolean = false,
    var ctime: Date = Date(),
) {
    companion object {
        const val TABLE_NAME = "character"
    }
}

suspend fun updateFavorite(appDatabase: AppDatabase, character: Character) {
    appDatabase.characterDao()
        .insert(character.toCharacterEntity().copy(favorite = !character.favorite))
}

private fun Character.toCharacterEntity() = CharacterEntity(
    charId = charId,
    name = name,
    img = img,
    nickname = nickname
)
