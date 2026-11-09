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

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.core.theme.BrbaPreviewTheme
import io.github.shinhyo.brba.core.ui.BrBaCircleProgress
import io.github.shinhyo.brba.core.ui.BrbaChips
import io.github.shinhyo.brba.core.ui.BrbaIconFavorite
import io.github.shinhyo.brba.core.utils.brbaSharedElement
import io.github.shinhyo.brba.feature.detail.navigaion.Detail

@Composable
context(scope: SharedTransitionScope)
fun DetailRoute(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    detail: Detail,
    viewModel: DetailViewModel = hiltViewModel<DetailViewModel, DetailViewModel.Factory>(
        creationCallback = { factory -> factory.create(detail) },
    ),
) {
    val imageState by viewModel.imageState.collectAsStateWithLifecycle()
    val infoState by viewModel.infoState.collectAsStateWithLifecycle()

    DetailScreen(
        modifier = modifier.statusBarsPadding(),
        infoState = infoState,
        imageState = imageState,
        animatedVisibilityScope = animatedVisibilityScope,
        onFavoriteClick = viewModel::updateFavorite,
    )
}

@Composable
context(scope: SharedTransitionScope)
private fun DetailScreen(
    modifier: Modifier = Modifier,
    infoState: DetailInfoState,
    imageState: Pair<Long, String>,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onFavoriteClick: (BrbaCharacter) -> Unit,
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                CharacterImage(
                    animatedVisibilityScope = animatedVisibilityScope,
                    imageState = imageState,
                )
            }

            when (val info: DetailInfoState = infoState) {
                is DetailInfoState.Loading -> {
                    item {
                        BrBaCircleProgress(modifier = Modifier)
                    }
                }

                is DetailInfoState.Success -> {
                    val character = info.character
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = character.name,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .weight(1.0f),
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            BrbaIconFavorite(
                                enable = character.isFavorite,
                                modifier = Modifier
                                    .size(24.dp),
                            ) {
                                onFavoriteClick.invoke(character)
                            }
                        }
                    }

                    item {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            text = character.nickname,
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }

                    if (character.category.isNotEmpty()) {
                        item {
                            BrbaChips(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp),
                                chipList = character.category.map { "#$it" },
                            )
                        }
                    }

                    item {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            text = character.description,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                is DetailInfoState.Error -> {
                    // TODO:
                }
            }
        }
    }
}

@Composable
context(scope: SharedTransitionScope)
private fun CharacterImage(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    imageState: Pair<Long, String>,
) {
    val (id, image) = imageState
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .build(),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        alignment = Alignment.TopCenter,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f / 1.4f)
            .brbaSharedElement(
                isLocalInspectionMode = LocalInspectionMode.current,
                animatedVisibilityScope = animatedVisibilityScope,
                scope.rememberSharedContentState(key = "character_${id}_row"),
                scope.rememberSharedContentState(key = "character_${id}_card"),
            ),
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    BrbaPreviewTheme {
        DetailScreen(
            animatedVisibilityScope = it,
            infoState = DetailInfoState.Success(
                character = BrbaCharacter(
                    charId = 0,
                    name = "Walter White Walter White Walter White Walter White Walter White",
                    birthday = "09-07-1958",
                    img = "https://~~~.jpg",
                    status = "Presumed dead",
                    nickname = "Heisenberg, Heisenberg, Heisenberg",
                    portrayed = "",
                    category = listOf(
                        "Breaking Bad1",
                        "Breaking Bad3",
                        "Breaking Bad5",
                        "Better Call Saul6",
                    ),
                    description = "Walter Walter Walter Walter Walter Walter Walter Walter Walter Walter ",
                    ratio = 1.5f,
                    isFavorite = false,
                ),
            ),
            imageState = 0L to "",
            onFavoriteClick = {},
        )
    }
}

@Preview
@Composable
private fun PreviewLoading() {
    BrbaPreviewTheme {
        DetailScreen(
            animatedVisibilityScope = it,
            infoState = DetailInfoState.Loading,
            imageState = 0L to "",
            onFavoriteClick = {},
        )
    }
}