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