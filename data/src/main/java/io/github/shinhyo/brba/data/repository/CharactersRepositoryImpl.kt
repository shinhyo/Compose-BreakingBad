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
import io.github.shinhyo.brba.domain.result.Result
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

open class CharactersRepositoryImpl @Inject constructor(
    private val api: BaBrApi,
    private val db: AppDatabase
) : CharactersRepository {

    companion object {
        const val MIN_RATIO = 1.2f
    }

    private val random: Random by lazy { Random(Calendar.getInstance().timeInMillis) }

    override fun getCharacterList(): Flow<Result<List<Character>>> {

        fun changeRatio(list: List<Character>) =
            list.map { c ->
                val nextInt = random.nextInt(4) * 0.15f
                c.copy(ratio = MIN_RATIO + nextInt)
            }

        fun addFavoriteToList(list: List<Character>) =
            db.characterDao().getAll()
                .map { dblist ->
                    list.toMutableList().map {
                        it.copy(
                            favorite = dblist.find { i ->
                                it.charId == i.charId
                            }?.favorite ?: false
                        )
                    }
                }

        return flow { emit(api.getCharacters()) }
            .map { it.map { r -> r.toCharacter() } }
            .map { changeRatio(it) }
            .flatMapConcat { addFavoriteToList(it) }
            .map { Result.Success(it) }
    }


    override fun getFavoriteList(isAsc: Boolean): Flow<List<Character>> =
        db.characterDao().getFavorite(isAsc = isAsc)
            .map { it.map { i -> i.toCharacter() } }

    override fun getCharacterById(id: Long): Flow<Character> =
        flow { emit(api.getCharactersById(id)) }
            .map { it[0] }
            .map { it.toCharacter() }
            .combine(
                db.characterDao().getCharacter(id)
            ) { res: Character, entity: CharacterEntity? ->
                res.copy(favorite = entity?.favorite ?: false)
            }

    override fun updateFavorite(character: Character): Flow<Boolean> = flowOf(character)
        .map { it.toCharacterEntity().copy(favorite = !character.favorite) }
        .map { db.characterDao().insert(it) }
        .map { it != 0L }
}
