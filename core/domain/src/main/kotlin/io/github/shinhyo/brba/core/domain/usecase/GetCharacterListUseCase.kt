/*
 * Copyright 2022 shinhyo
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
package io.github.shinhyo.brba.core.domain.usecase

import io.github.shinhyo.brba.core.common.di.BrbaDispatcher
import io.github.shinhyo.brba.core.common.di.Dispatcher
import io.github.shinhyo.brba.core.domain.repository.CharactersRepository
import io.github.shinhyo.brba.core.model.BrbaCharacter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject
import kotlin.random.Random

class GetCharacterListUseCase @Inject constructor(
    @Dispatcher(BrbaDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val repo: CharactersRepository,
) {
    companion object {
        private const val MIN_RATIO = 1.4f
    }

    private val random: Random by lazy { Random(Calendar.getInstance().timeInMillis) }

    operator fun invoke(): Flow<List<BrbaCharacter>> = repo.getCharacterList()
        .map {
            it.map { i -> i.copy(ratio = MIN_RATIO + random.nextInt(4) * 0.12f) }
        }
        .combine(
            repo.getDatabaseList(),
        ) { listApi, listDb ->
            listApi.map { item ->
                item.copy(
                    isFavorite = listDb.find { it.charId == item.charId }?.isFavorite ?: false,
                )
            }
        }
        .flowOn(ioDispatcher)
}