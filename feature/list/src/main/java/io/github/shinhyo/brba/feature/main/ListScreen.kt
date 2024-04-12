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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.core.ui.BrBaCircleProgress
import io.github.shinhyo.brba.core.ui.BrbaCharacterCard
import io.github.shinhyo.brba.core.ui.BrbaTopAppBar

@Composable
internal fun ListRoute(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel(),
    navigateToDetail: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ListScreen(
        modifier = modifier,
        uiState = uiState,
        onCharacterClick = navigateToDetail,
        onFavoriteClick = viewModel::updateFavorite
    )
}

@Composable
private fun ListScreen(
    modifier: Modifier = Modifier,
    uiState: ListUiState,
    onCharacterClick: (Long) -> Unit = {},
    onFavoriteClick: (BrbaCharacter) -> Unit = {}
) {
    val hazeState: HazeState = remember { HazeState() }

    Scaffold(
        topBar = {
            BrbaTopAppBar(
                hazeState = hazeState
            )
        },
        contentWindowInsets = WindowInsets(16.dp, 4.dp, 16.dp, 16.dp)
    ) { contentPadding ->

        when (uiState) {
            is ListUiState.Loading -> {
                BrBaCircleProgress(modifier)
            }

            is ListUiState.Error -> {
                uiState.exception?.printStackTrace()
            }

            is ListUiState.Success -> {
                LazyVerticalStaggeredGrid(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalItemSpacing = 6.dp,
                    contentPadding = contentPadding,
                    columns = StaggeredGridCells.Adaptive(
                        minSize = 100.dp
                    ),
                    modifier = modifier
                        .haze(
                            state = hazeState,
                            style = HazeDefaults.style(backgroundColor = MaterialTheme.colorScheme.surface)
                        )
                        .fillMaxSize()
                ) {
                    items(
                        items = uiState.list,
                        key = { it.charId }
                    ) { character ->
                        BrbaCharacterCard(
                            character = character,
                            onCharacterClick = onCharacterClick,
                            onFavoriteClick = onFavoriteClick
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun Preview() {
    val character = BrbaCharacter(
        charId = 0,
        name = "Walter White",
        birthday = "09-07-1958",
        img = "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
        status = "Presumed dead",
        nickname = "Heisenberg",
        portrayed = "",
        category = "Breaking Bad",
        ratio = 1.2f,
        isFavorite = true,
        ctime = null
    )
    ListScreen(
        uiState = ListUiState.Success(
            listOf(
                character,
                character.copy(charId = 1, ratio = 1.8f),
                character.copy(charId = 2, ratio = 1.6f, isFavorite = false),
                character.copy(charId = 3, ratio = 1.4f, isFavorite = false)
            )
        )
    )
}