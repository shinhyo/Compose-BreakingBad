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
package io.github.shinhyo.brba.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.shinhyo.brba.core.common.result.Result
import io.github.shinhyo.brba.core.common.result.asResult
import io.github.shinhyo.brba.core.domain.GetCharacterUseCase
import io.github.shinhyo.brba.core.domain.UpdateFavoriteUseCase
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.feature.detail.navigaion.DetailArgs
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface DetailUiState {
    data class Success(val character: BrbaCharacter) : DetailUiState
    data class Error(val exception: Throwable? = null) : DetailUiState
    data object Loading : DetailUiState
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCharacterUseCase: GetCharacterUseCase,
    val updateFavoriteUseCase: UpdateFavoriteUseCase,
) : ViewModel() {

    private val args by lazy { DetailArgs(savedStateHandle = savedStateHandle) }

    val uiState = getCharacterUseCase(args.characterId)
        .asResult()
        .map {
            when (it) {
                is Result.Loading -> DetailUiState.Loading
                is Result.Success -> DetailUiState.Success(it.data)
                is Result.Error -> DetailUiState.Error(it.exception)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailUiState.Loading
        )

    fun updateFavorite(character: BrbaCharacter) {
        updateFavoriteUseCase(character)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }
}
