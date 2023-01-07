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

import com.google.gson.annotations.SerializedName
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.core.network.model.Character.Data

data class Character(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: List<Data> = emptyList()
) {
    data class Data(
        @SerializedName("birthday")
        val birthday: String?,
        @SerializedName("category")
        val category: String?,
        @SerializedName("char_id")
        val charId: Long?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("img")
        val img: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("nickname")
        val nickname: String?,
        @SerializedName("portrayed_by")
        val portrayedBy: String?,
        @SerializedName("status")
        val status: String?
    )
}

fun Data.asExternalModel(): BrbaCharacter {
    return BrbaCharacter(
        charId = charId ?: 0,
        name = name ?: "",
        birthday = birthday ?: "",
        img = img ?: "",
        status = status ?: "",
        nickname = nickname ?: "",
        category = category ?: "",
    )
}
