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
package io.github.shinhyo.brba.domain.usecase

import io.github.shinhyo.brba.domain.di.IoDispatcher
import io.github.shinhyo.brba.domain.model.Character
import io.github.shinhyo.brba.domain.repository.CharactersRepository
import io.github.shinhyo.brba.domain.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterListUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : FlowUseCase<Unit, List<Character>>(dispatcher) {

    override fun execute(parameters: Unit): Flow<Result<List<Character>>> =
        charactersRepository.getCharacterList()
}
