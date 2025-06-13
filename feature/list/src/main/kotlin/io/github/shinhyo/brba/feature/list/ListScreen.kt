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
package io.github.shinhyo.brba.feature.list

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.core.model.BrbaThemeMode
import io.github.shinhyo.brba.core.theme.BrbaPreviewTheme
import io.github.shinhyo.brba.core.ui.BrBaCircleProgress
import io.github.shinhyo.brba.core.ui.BrbaCharacterCard
import io.github.shinhyo.brba.core.ui.BrbaTopAppBar

@Composable
fun SharedTransitionScope.ListRoute(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: ListViewModel = hiltViewModel(),
    navigateToDetail: (BrbaCharacter) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ListScreen(
        modifier = modifier,
        uiState = uiState,
        onCharacterClick = navigateToDetail,
        onFavoriteClick = viewModel::onFavoriteClick,
        animatedVisibilityScope = animatedVisibilityScope,
        onChangeThemeClick = viewModel::onChangeThemeClick,
    )
}

@Composable
private fun SharedTransitionScope.ListScreen(
    modifier: Modifier = Modifier,
    uiState: ListUiState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onCharacterClick: (BrbaCharacter) -> Unit = {},
    onFavoriteClick: (BrbaCharacter) -> Unit = {},
    onChangeThemeClick: (BrbaThemeMode) -> Unit = {},
) {
    val hazeState = remember { HazeState() }

    Scaffold(
        topBar = {
            ListTopAppBar(
                hazeState = hazeState,
                uiState = uiState,
                onChangeThemeClick = onChangeThemeClick,
            )
        },
        contentWindowInsets = WindowInsets(16.dp, 4.dp, 16.dp, 16.dp),
    ) { contentPadding ->
        when (uiState) {
            is ListUiState.Loading -> LoadingContent()
            is ListUiState.Error -> ErrorContent(uiState.exception)
            is ListUiState.Success -> SuccessContent(
                modifier = modifier,
                animatedVisibilityScope = animatedVisibilityScope,
                contentPadding = contentPadding,
                hazeState = hazeState,
                characters = uiState.characters,
                onCharacterClick = onCharacterClick,
                onFavoriteClick = onFavoriteClick,
            )
        }
    }
}

@Composable
private fun ListTopAppBar(
    hazeState: HazeState,
    uiState: ListUiState,
    onChangeThemeClick: (BrbaThemeMode) -> Unit,
) {
    BrbaTopAppBar(
        hazeState = hazeState,
        actions = {
            if (uiState is ListUiState.Success) {
                ThemeToggleButton(
                    themeMode = uiState.themeMode,
                    onClick = onChangeThemeClick,
                )
            }
        },
    )
}

@Composable
private fun LoadingContent() {
    BrBaCircleProgress(Modifier.fillMaxSize())
}

@Composable
private fun ErrorContent(exception: Throwable?) {
    // Display error message
}

@Composable
private fun SharedTransitionScope.SuccessContent(
    modifier: Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    contentPadding: PaddingValues,
    hazeState: HazeState,
    characters: List<BrbaCharacter>,
    onCharacterClick: (BrbaCharacter) -> Unit,
    onFavoriteClick: (BrbaCharacter) -> Unit,
) {
    val columns = remember { StaggeredGridCells.Adaptive(minSize = 100.dp) }

    LazyVerticalStaggeredGrid(
        columns = columns,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalItemSpacing = 6.dp,
        contentPadding = contentPadding,
        modifier = modifier
            .hazeSource(state = hazeState)
            .fillMaxSize(),
    ) {
        items(
            items = characters,
            key = { it.charId },
            contentType = { "character" },
        ) { character ->
            CharacterCard(
                animatedVisibilityScope = animatedVisibilityScope,
                character = character,
                onCharacterClick = onCharacterClick,
                onFavoriteClick = onFavoriteClick,
            )
        }
    }
}

@Composable
private fun SharedTransitionScope.CharacterCard(
    animatedVisibilityScope: AnimatedVisibilityScope,
    character: BrbaCharacter,
    onCharacterClick: (BrbaCharacter) -> Unit,
    onFavoriteClick: (BrbaCharacter) -> Unit,
) {
    BrbaCharacterCard(
        modifier = Modifier,
        animatedVisibilityScope = animatedVisibilityScope,
        character = character,
        onCharacterClick = onCharacterClick,
        onFavoriteClick = onFavoriteClick,
    )
}

@Composable
private fun ThemeToggleButton(
    themeMode: BrbaThemeMode,
    onClick: (BrbaThemeMode) -> Unit,
) {
    if (themeMode != BrbaThemeMode.System) {
        val iconRes = if (themeMode == BrbaThemeMode.Light) {
            io.github.shinhyo.brba.core.designsystem.R.drawable.ic_theme_light
        } else {
            io.github.shinhyo.brba.core.designsystem.R.drawable.ic_theme_dark
        }
        IconButton(onClick = { onClick(themeMode) }) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "Change theme",
                tint = MaterialTheme.colorScheme.tertiary,
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    BrbaPreviewTheme {
        val character = BrbaCharacter(
            charId = 0,
            name = "Walter White",
            birthday = "09-07-1958",
            img = "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
            status = "Presumed dead",
            nickname = "Heisenberg",
            portrayed = "",
            category = listOf("Breaking Bad"),
            ratio = 1.2f,
            isFavorite = true,
        )
        ListScreen(
            uiState = ListUiState.Success(
                characters = listOf(
                    character,
                    character.copy(charId = 1, ratio = 1.8f),
                    character.copy(charId = 2, ratio = 1.6f, isFavorite = false),
                    character.copy(charId = 3, ratio = 1.4f, isFavorite = false),
                    character.copy(charId = 4, ratio = 1.2f, isFavorite = false),
                    character.copy(charId = 5, ratio = 1.8f, isFavorite = true),
                ),
                themeMode = BrbaThemeMode.System,
            ),
            animatedVisibilityScope = it,
        )
    }
}