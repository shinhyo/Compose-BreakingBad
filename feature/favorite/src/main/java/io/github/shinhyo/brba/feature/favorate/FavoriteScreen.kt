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
package io.github.shinhyo.brba.feature.favorate

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import io.github.shinhyo.brba.core.theme.BrbaPreviewTheme
import io.github.shinhyo.brba.core.ui.BrBaCircleProgress
import io.github.shinhyo.brba.core.ui.BrbaCharacterRow
import io.github.shinhyo.brba.core.ui.BrbaEmptyScreen
import io.github.shinhyo.brba.core.ui.BrbaTopAppBar

@Composable
fun SharedTransitionScope.FavoriteRoute(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: FavoriteViewModel = hiltViewModel(),
    onCharacterClick: (BrbaCharacter) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoriteScreen(
        modifier = modifier,
        uiState = uiState,
        animatedVisibilityScope = animatedVisibilityScope,
        onCharacterClick = onCharacterClick,
        onFavoriteClick = viewModel::updateFavorite,
    )
}

@Composable
private fun SharedTransitionScope.FavoriteScreen(
    modifier: Modifier = Modifier,
    uiState: FavoriteUiState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onCharacterClick: (BrbaCharacter) -> Unit = {},
    onFavoriteClick: (BrbaCharacter) -> Unit = {},
) {
    val hazeState: HazeState = remember { HazeState() }

    Scaffold(
        topBar = {
            BrbaTopAppBar(
                hazeState = hazeState,
                title = "Favorite",
            )
        },
        contentWindowInsets = WindowInsets(16.dp, 4.dp, 16.dp, 16.dp),
        modifier = Modifier
            .fillMaxSize(),
    ) { contentPadding ->

        when (uiState) {
            is FavoriteUiState.Loading -> {
                BrBaCircleProgress()
            }

            is FavoriteUiState.Success -> {
                LazyColumn(
                    modifier = modifier
                        .haze(
                            state = hazeState,
                            style = HazeDefaults.style(backgroundColor = MaterialTheme.colorScheme.surface),
                        ),
                    state = rememberLazyListState(),
                    contentPadding = contentPadding,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(
                        items = uiState.list,
                        key = { item -> item.charId },
                    ) { item ->
                        BrbaCharacterRow(
                            character = item,
                            animatedVisibilityScope = animatedVisibilityScope,
                            onCharacterClick = onCharacterClick,
                            onFavoriteClick = onFavoriteClick,
                        )
                    }
                }
            }

            is FavoriteUiState.Empty -> {
                BrbaEmptyScreen()
            }

            is FavoriteUiState.Error -> {
                uiState.exception?.printStackTrace()
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    BrbaPreviewTheme {
        FavoriteScreen(
            uiState = FavoriteUiState.Success(
                list = listOf(
                    BrbaCharacter(
                        charId = 0,
                        name = "Walter White Walter White Walter White",
                        birthday = "09-07-1958",
                        img = "https://~~~.jpg",
                        status = "Presumed dead",
                        nickname = "Heisenberg, Heisenberg, Heisenberg, Heisenberg",
                        portrayed = "",
                        category = "Breaking Bad",
                        ratio = 1.5f,
                        isFavorite = true,
                        ctime = null,
                    ),
                ),
            ),
            onCharacterClick = {},
            animatedVisibilityScope = it,
        )
    }
}