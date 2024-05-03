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
import io.github.shinhyo.brba.core.domain.usecase.GetCharacterUseCase
import io.github.shinhyo.brba.core.domain.usecase.UpdateFavoriteUseCase
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.feature.detail.navigaion.DetailArgs
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface DetailListType {

    data class Image(
        val id: Long,
        val image: String,
    ) : DetailListType

    data object Loading : DetailListType

    data class Description(
        val character: BrbaCharacter,
    ) : DetailListType

    data class Error(
        val exception: Throwable,
    ) : DetailListType
}

sealed class DetailUiState(
    open val items: List<DetailListType>,
) {
    data class Init(override val items: List<DetailListType>) : DetailUiState(items)
    data class Loading(override val items: List<DetailListType>) : DetailUiState(items)
    data class Success(override val items: List<DetailListType>) : DetailUiState(items)
    data class Error(override val items: List<DetailListType>) : DetailUiState(items)
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getCharacterUseCase: GetCharacterUseCase,
    val updateFavoriteUseCase: UpdateFavoriteUseCase,
) : ViewModel() {

    private val args: DetailArgs by lazy { DetailArgs(savedStateHandle = savedStateHandle) }

    val uiState = getCharacterUseCase(id = args.id)
        .asResult()
        .map {
            when (it) {
                is Result.Loading -> DetailUiState.Loading(
                    listOf(
                        DetailListType.Image(
                            id = args.id,
                            image = args.image,
                        ),
                        DetailListType.Loading,
                    ),
                )

                is Result.Success -> {
                    val character: BrbaCharacter = it.data
                    DetailUiState.Success(
                        listOf(
                            DetailListType.Image(
                                id = args.id,
                                image = args.image,
                            ),
                            DetailListType.Description(
                                character = character,
                            ),
                        ),
                    )
                }

                is Result.Error -> DetailUiState.Error(
                    listOf(
                        DetailListType.Error(
                            exception = it.exception,
                        ),
                    ),
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailUiState.Init(
                listOf(
                    DetailListType.Image(
                        id = args.id,
                        image = args.image,
                    ),
                ),
            ),
        )

    fun updateFavorite(character: BrbaCharacter) {
        updateFavoriteUseCase(character)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }
}