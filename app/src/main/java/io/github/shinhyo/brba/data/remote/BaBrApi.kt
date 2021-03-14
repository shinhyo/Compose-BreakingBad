package io.github.shinhyo.brba.data.remote

import io.github.shinhyo.brba.data.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BaBrApi {

    companion object {
        const val BASE_URL = "https://www.breakingbadapi.com"
    }

    @GET("/api/characters")
    suspend fun getCharacters(): List<CharacterResponse>

    @GET("/api/characters/{id}")
    suspend fun getCharactersById(@Path("id") id: Long): List<CharacterResponse>


}