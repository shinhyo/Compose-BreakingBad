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
package io.github.shinhyo.brba.domain.usecase

import io.github.shinhyo.brba.domain.model.Character
import io.github.shinhyo.brba.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject
import kotlin.random.Random

class GetCharacterListUseCase @Inject constructor(
    private val repo: CharactersRepository
) {
    private val random: Random by lazy { Random(Calendar.getInstance().timeInMillis) }

    fun execute(): Flow<List<Character>> = repo.getCharacterList()
        .map { it.map { c -> c.copy(ratio = random.nextInt(4).let { r -> 1.4f + r * 0.15f }) } }
}

class GetFavoriteListUseCase @Inject constructor(
    private val repo: CharactersRepository
) {
    fun execute(isAsc: Boolean = false): Flow<List<Character>> = repo.getFavoriteList(isAsc)
}

class GetCharacterUseCase @Inject constructor(
    private val repo: CharactersRepository
) {
    fun execute(id: Long): Flow<Character> = repo.getCharacterById(id)
}

class AddFavoriteToListUseCase @Inject constructor(
    private val repo: CharactersRepository
) {
    fun execute(list: List<Character>): Flow<List<Character>> = repo.addFavoriteStatus(list)
}

class UpdateFavoriteUseCase @Inject constructor(
    private val repo: CharactersRepository
) {
    fun execute(character: Character): Flow<Boolean> = repo.updateFavorite(character)
}
