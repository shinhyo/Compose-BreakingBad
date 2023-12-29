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
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn

class GetCharacterUseCase @Inject constructor(
    @Dispatcher(BrbaDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val repo: CharactersRepository
) {
    operator fun invoke(
        id: Long
    ): Flow<BrbaCharacter> = repo.getCharacterList(id = id)
        .combine(
            repo.getDatabaseList(id = id)
        ) { api: BrbaCharacter, db: BrbaCharacter? ->
            api.copy(favorite = db?.favorite ?: false)
        }.flowOn(ioDispatcher)
}
