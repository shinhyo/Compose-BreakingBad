package io.github.shinhyo.brba.domain.repository

import io.github.shinhyo.brba.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getCharacterList(): Flow<List<Character>>
    fun getFavoriteList(isAsc: Boolean = false): Flow<List<Character>>
    fun getCharacterById(id: Long): Flow<Character>
    fun addFavoriteStatus(listCharacter: List<Character>): Flow<List<Character>>
    fun updateFavorite(character: Character): Flow<Boolean>
}