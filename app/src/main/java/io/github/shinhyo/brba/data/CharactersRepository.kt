package io.github.shinhyo.brba.data

import io.github.shinhyo.brba.data.remote.BaBrApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

interface CharactersRepository {
    fun getCharacter(id: Long? = null): Flow<List<Character>>
}

open class CharactersRepositoryImpl(private val api: BaBrApi) : CharactersRepository {
    override fun getCharacter(id: Long?): Flow<List<Character>> = flow {
        if (id == null) emit(api.getCharacters()) else emit(api.getCharactersById(id))
    }.map { it.map { r -> r.toCharacter() } }
}
