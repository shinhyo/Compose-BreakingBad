package io.github.shinhyo.brba.domain.usecase

import io.github.shinhyo.brba.domain.model.Character
import io.github.shinhyo.brba.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.util.Calendar
import kotlin.random.Random

class GetCharacterListUseCase(private val repo: CharactersRepository) {

    private val random = Random(Calendar.getInstance().timeInMillis)

    fun execute(): Flow<List<Character>> = repo.getCharacterList()
        .onEach { Timber.i("execute: $it") }
        .map { it.map { c -> c.copy(ratio = random.nextInt(4).let { r -> 1.4f + r * 0.15f }) } }
}

class GetFavoriteListUseCase(private val repo: CharactersRepository) {
    fun execute(isAsc: Boolean = false): Flow<List<Character>> = repo.getFavoriteList(isAsc)
}

class GetCharacterUseCase(private val repo: CharactersRepository) {
    fun execute(id: Long): Flow<Character> = repo.getCharacterById(id)
}

class AddFavoriteToListUseCase(private val repo: CharactersRepository) {
    fun execute(list: List<Character>): Flow<List<Character>> = repo.addFavoriteStatus(list)
}

class UpdateFavoriteUseCase(private val repo: CharactersRepository) {
    fun execute(character: Character): Flow<Boolean> = repo.updateFavorite(character)
}