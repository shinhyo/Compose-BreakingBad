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
package io.github.shinhyo.brba.data

import io.github.shinhyo.brba.data.remote.BaBrApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

interface CharactersRepository {
    fun getCharacter(id: Long? = null): Flow<List<Character>>
}

open class CharactersRepositoryImpl(private val api: BaBrApi) : CharactersRepository {
    override fun getCharacter(id: Long?): Flow<List<Character>> = flow {
        if (id == null) emit(api.getCharacters()) else emit(api.getCharactersById(id))
    }.map { it.map { r -> r.toCharacter() } }
}
