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
package io.github.shinhyo.brba.presentation.ui.list

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.shinhyo.brba.domain.model.Character
import io.github.shinhyo.brba.domain.result.Result
import io.github.shinhyo.brba.domain.usecase.GetCharacterListUseCase
import io.github.shinhyo.brba.domain.usecase.UpdateFavoriteUseCase
import io.github.shinhyo.brba.presentation.R
import io.github.shinhyo.brba.presentation.ui.BaseFavoriteViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


data class ListUiState(
    val isLoading: Boolean = false,
    @StringRes val errorMessages: Int? = null,
    val list: List<Character> = emptyList()
)

@HiltViewModel
class ListViewModel
@Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase,
    updateFavoriteUseCase: UpdateFavoriteUseCase,
) : BaseFavoriteViewModel(updateFavoriteUseCase) {

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState = _uiState.asStateFlow()


    init {
        getCharacterList()
    }

    private fun getCharacterList() {
        _uiState.update { it.copy(isLoading = true) }

        getCharacterListUseCase(Unit)
            .onEach { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                list = result.data
                            )
                        }
                    }
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                errorMessages = R.string.error_load
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}
