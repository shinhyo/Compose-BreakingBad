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
package io.github.shinhyo.brba.presentation.ui.favorite

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.shinhyo.brba.domain.model.Character
import io.github.shinhyo.brba.domain.usecase.GetFavoriteListUseCase
import io.github.shinhyo.brba.domain.usecase.UpdateFavoriteUseCase
import io.github.shinhyo.brba.presentation.ui.BaseFavoriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
@Inject constructor(
    private val getFavoriteListUseCase: GetFavoriteListUseCase,
    updateFavoriteUseCase: UpdateFavoriteUseCase,
) : BaseFavoriteViewModel(updateFavoriteUseCase) {

    private val _list = MutableStateFlow(emptyList<Character>())
    val list get() = _list.asStateFlow()

    init {
        getFavoriteList()
    }

    private fun getFavoriteList(isAsc: Boolean = false) {
        getFavoriteListUseCase.execute(isAsc)
            .map { _list.value = it }
            .flowOn(Dispatchers.IO)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }
}
