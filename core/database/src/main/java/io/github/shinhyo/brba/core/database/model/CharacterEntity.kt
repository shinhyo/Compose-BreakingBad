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
package io.github.shinhyo.brba.core.database.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import io.github.shinhyo.brba.core.database.model.CharacterEntity.Companion.TABLE_NAME
import io.github.shinhyo.brba.core.model.BrbaCharacter
import java.util.Date

@Keep
@Entity(
    tableName = TABLE_NAME,
    indices = [(Index(value = ["charId"], unique = true))],
)
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

fun CharacterEntity.asExternalModel() = BrbaCharacter(
    charId = charId,
    name = name,
    img = img,
    nickname = nickname,
    isFavorite = favorite,
    ctime = ctime,
)