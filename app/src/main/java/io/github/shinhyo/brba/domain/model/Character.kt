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
package io.github.shinhyo.brba.domain.model

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import java.util.Date

@Immutable
data class Character(
    @SerializedName("char_id")
    val charId: Long = -1,
    val name: String = "",
    val birthday: String = "",
    val occupation: List<String> = listOf(),
    val img: String = "",
    val status: String = "",
    val nickname: String = "",
    val appearance: List<Int> = listOf(),
    val portrayed: String = "",
    val category: String = "",
    @SerializedName("better_call_saul_appearance")
    val betterCallSaulAppearance: List<Int> = listOf(),
    val ratio: Float = 1f,
    val favorite: Boolean = false,
    val ctime: Date? = null,
)
