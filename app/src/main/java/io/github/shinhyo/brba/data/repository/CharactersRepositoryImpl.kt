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
package io.github.shinhyo.brba.data.repository

import io.github.shinhyo.brba.data.local.db.AppDatabase
import io.github.shinhyo.brba.data.local.model.CharacterEntity
import io.github.shinhyo.brba.data.mapper.toCharacter
import io.github.shinhyo.brba.data.mapper.toCharacterEntity
import io.github.shinhyo.brba.data.remote.api.BaBrApi
import io.github.shinhyo.brba.domain.model.Character
import io.github.shinhyo.brba.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

open class CharactersRepositoryImpl(private val api: BaBrApi, private val db: AppDatabase) :
    CharactersRepository {

    override fun getCharacterList(): Flow<List<Character>> = flow { emit(api.getCharacters()) }
        .map { it.map { r -> r.toCharacter() } }

    override fun getFavoriteList(isAsc: Boolean): Flow<List<Character>> =
        db.characterDao().getFavorite(isAsc = isAsc)
            .map { it.map { i -> i.toCharacter() } }

    override fun getCharacterById(id: Long): Flow<Character> =
        flow { emit(api.getCharactersById(id)) }
            .map { it[0] }
            .map { it.toCharacter() }
            .combine(db.characterDao().getCharacter(id)) { res: Character, entity: CharacterEntity? ->
                res.copy(favorite = entity?.favorite ?: false)
            }

    override fun addFavoriteStatus(listCharacter: List<Character>): Flow<List<Character>> =
        flowOf(listCharacter)
            .combine(db.characterDao().getAll()) { list: List<Character>, db: List<CharacterEntity> ->
                list.toMutableList().map {
                    it.copy(favorite = db.find { i -> it.charId == i.charId }?.favorite ?: false)
                }
            }

    override fun updateFavorite(character: Character): Flow<Boolean> = flowOf(character)
        .map { it.toCharacterEntity().copy(favorite = !character.favorite) }
        .map { db.characterDao().insert(it) }
        .map { it != 0L }
}
