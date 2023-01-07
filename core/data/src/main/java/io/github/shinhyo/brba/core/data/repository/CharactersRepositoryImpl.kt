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
package io.github.shinhyo.brba.core.data.repository

import io.github.shinhyo.brba.core.data.model.asEntity
import io.github.shinhyo.brba.core.database.dao.CharacterDao
import io.github.shinhyo.brba.core.database.model.CharacterEntity
import io.github.shinhyo.brba.core.database.model.asExternalModel
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.core.network.NetworkDataSource
import io.github.shinhyo.brba.core.network.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject
import kotlin.random.Random

open class CharactersRepositoryImpl @Inject constructor(
    private val api: NetworkDataSource,
    private val characterDao: CharacterDao,
) : CharactersRepository {

    companion object {
        const val MIN_RATIO = 1.2f
    }

    private val random: Random by lazy { Random(Calendar.getInstance().timeInMillis) }

    override fun getCharacterList(): Flow<List<BrbaCharacter>> {

        fun changeRatio(list: List<BrbaCharacter>) = list.map { c ->
            val nextInt = random.nextInt(4) * 0.2f
            c.copy(ratio = MIN_RATIO + nextInt)
        }

        fun addFavoriteToList(list: List<BrbaCharacter>) = characterDao.getAll()
            .map { entityList ->
                list.toMutableList().map {
                    it.copy(
                        favorite = entityList.find { i ->
                            it.charId == i.charId
                        }?.favorite ?: false
                    )
                }
            }

        return flow { emit(api.getCharacters()) }
            .map { it.data.map { character -> character.asExternalModel() } }
            .map { changeRatio(it) }
            .flatMapConcat { addFavoriteToList(it) }
    }

    override fun getFavoriteList(isAsc: Boolean): Flow<List<BrbaCharacter>> =
        characterDao.getFavorite(isAsc = isAsc)
            .map { it.map { i -> i.asExternalModel() } }

    override fun getCharacterById(id: Long): Flow<BrbaCharacter> =
        flow { emit(api.getCharactersById(id)) }
            .map { it.data.first().asExternalModel() }
            .combine(characterDao.getCharacter(id)) { res: BrbaCharacter, entity: CharacterEntity? ->
                res.copy(favorite = entity?.favorite ?: false)
            }

    override fun updateFavorite(character: BrbaCharacter): Flow<Boolean> = flowOf(character)
        .map { it.asEntity().copy(favorite = !character.favorite) }
        .map { characterDao.insert(it) }
        .map { it != 0L }
}
