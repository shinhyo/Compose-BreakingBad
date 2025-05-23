/*
 * Copyright 2025 shinhyo
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
package io.github.shinhyo.brba.core.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.shinhyo.brba.core.network.model.CharacterResponse
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * NOTE: This is a sample app.
 * The actual network implementation can be replaced with a local data source
 * for testing or demo purposes without real network calls.
 */
@Singleton
class LocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
) : NetworkDataSource {
    private val jsonParser = Json { ignoreUnknownKeys = true }

    override suspend fun getCharacter(): List<CharacterResponse> {
        simulateNetworkDelay()
        return readCharactersFromAsset()
    }

    override suspend fun getCharacter(id: Long): List<CharacterResponse> {
        simulateNetworkDelay()
        return readCharactersFromAsset().filter { it.charId == id }
    }

    private suspend fun simulateNetworkDelay() {
        // Simulate random network delay (up to 500ms) for sample app
        delay((0..500).random().toLong())
    }

    private fun readCharactersFromAsset(): List<CharacterResponse> {
        val assetManager = context.assets
        val inputStream = assetManager.open("character.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        return try {
            jsonParser.decodeFromString(json)
        } catch (e: Exception) {
            Timber.e(e, "Failed to parse character.json")
            emptyList()
        }
    }
}