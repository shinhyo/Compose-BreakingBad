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
package io.github.shinhyo.brba.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.shinhyo.brba.core.common.result.Result
import io.github.shinhyo.brba.core.common.result.asResult
import io.github.shinhyo.brba.core.domain.repository.DeviceRepository
import io.github.shinhyo.brba.core.domain.usecase.GetCharacterListUseCase
import io.github.shinhyo.brba.core.domain.usecase.UpdateFavoriteUseCase
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.core.model.BrbaThemeMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface ListUiState {
    data class Success(
        val characters: List<BrbaCharacter>,
        val themeMode: BrbaThemeMode,
    ) : ListUiState

    data class Error(val exception: Throwable? = null) : ListUiState
    data object Loading : ListUiState
}

@HiltViewModel
class ListViewModel @Inject constructor(
    getCharacterListUseCase: GetCharacterListUseCase,
    val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val deviceRepository: DeviceRepository,
) : ViewModel() {

    val uiState = combine(
        getCharacterListUseCase(),
        deviceRepository.deviceData,
    ) { characters, deviceData ->
        characters to deviceData.themeMode
    }
        .asResult()
        .map { result ->
            when (result) {
                is Result.Loading -> ListUiState.Loading
                is Result.Success -> {
                    val (character, themeMode) = result.data
                    ListUiState.Success(character, themeMode)
                }

                is Result.Error -> ListUiState.Error(result.exception)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ListUiState.Loading,
        )

    fun onFavoriteClick(character: BrbaCharacter) {
        updateFavoriteUseCase(character)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }

    fun onChangeThemeClick(currentMode: BrbaThemeMode) {
        flowOf(
            when (currentMode) {
                BrbaThemeMode.Light -> BrbaThemeMode.Dark
                BrbaThemeMode.Dark -> BrbaThemeMode.Light
                BrbaThemeMode.System -> throw IllegalArgumentException()
            },
        )
            .onEach { mode -> deviceRepository.setThemeMode(mode) }
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }
}