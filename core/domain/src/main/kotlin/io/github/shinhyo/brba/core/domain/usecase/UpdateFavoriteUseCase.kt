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

import io.github.shinhyo.brba.core.common.di.BrbaDispatcher.IO
import io.github.shinhyo.brba.core.common.di.Dispatcher
import io.github.shinhyo.brba.core.domain.repository.CharactersRepository
import io.github.shinhyo.brba.core.model.BrbaCharacter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateFavoriteUseCase @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val repo: CharactersRepository,
) {
    operator fun invoke(character: BrbaCharacter): Flow<Boolean> = repo.updateFavorite(character)
        .flowOn(ioDispatcher)
}