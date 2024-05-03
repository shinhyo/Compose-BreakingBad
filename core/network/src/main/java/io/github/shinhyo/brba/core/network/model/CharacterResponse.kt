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
package io.github.shinhyo.brba.core.network.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.shinhyo.brba.core.model.BrbaCharacter

@Keep
data class CharacterResponse(
    @SerializedName("char_id")
    val charId: Long?,
    val name: String?,
    val birthday: String?,
    val img: String?,
    val status: String?,
    val nickname: String?,
    val portrayed: String?,
    val category: String?,
)

fun CharacterResponse.asExternalModel() = BrbaCharacter(
    charId = charId ?: -1,
    name = name ?: "",
    birthday = birthday ?: "",
    img = img ?: "",
    status = status ?: "",
    nickname = nickname ?: "",
    portrayed = portrayed ?: "",
    category = category ?: "",
)