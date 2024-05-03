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
import io.github.shinhyo.brba.core.domain.repository.CharactersRepository
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.core.network.NetworkDataSource
import io.github.shinhyo.brba.core.network.model.CharacterResponse
import io.github.shinhyo.brba.core.network.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

open class CharactersRepositoryImpl @Inject constructor(
    private val api: NetworkDataSource,
    private val dao: CharacterDao,
) : CharactersRepository {

    override fun getCharacterList(): Flow<List<BrbaCharacter>> = flow { emit(api.getCharacter()) }
        .map { it.map(CharacterResponse::asExternalModel) }

    override fun getCharacterList(id: Long): Flow<BrbaCharacter> =
        flow { emit(api.getCharacter(id)) }
            .map { it.first().asExternalModel() }

    override fun getDatabaseList(isAsc: Boolean): Flow<List<BrbaCharacter>> =
        dao.getCharacter(isAsc = isAsc)
            .map { it.map(CharacterEntity::asExternalModel) }

    override fun getDatabaseList(id: Long): Flow<BrbaCharacter?> = dao.getCharacter(charId = id)
        .map { it?.asExternalModel() }

    override fun updateFavorite(character: BrbaCharacter): Flow<Boolean> = flowOf(character)
        .map { it.asEntity().copy(favorite = !character.isFavorite) }
        .map { dao.insert(it) }
        .map { it != 0L }
}