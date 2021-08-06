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
package io.github.shinhyo.brba.data.remote.api

import io.github.shinhyo.brba.data.remote.model.CharacterResponse
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
