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
package io.github.shinhyo.brba.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.shinhyo.brba.data.Character
import io.github.shinhyo.brba.data.local.AppDatabase
import io.github.shinhyo.brba.data.local.updateFavorite
import io.github.shinhyo.brba.data.toCharacter
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
@Inject constructor(
    private val db: AppDatabase
) : ViewModel() {

    private val _list = MutableStateFlow(emptyList<Character>())
    val list get() = _list.asStateFlow()

    init {
        getFavoriteList()
    }

    private fun getFavoriteList(isAsc: Boolean = false) {
        db.characterDao().getFavorite(isAsc = isAsc)
            .map { _list.value = it.map { i -> i.toCharacter() } }
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }

    fun upsertFavorite(character: Character) {
        viewModelScope.launch { updateFavorite(db, character) }
    }
}
