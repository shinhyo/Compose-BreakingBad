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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.core.ui.BrBaCircleProgress
import io.github.shinhyo.brba.core.ui.BrbaIconFavorite

@Composable
fun DetailRoute(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetailScreen(
        modifier = modifier.statusBarsPadding(),
        uiState = uiState,
        onFavoriteClick = viewModel::updateFavorite
    )
}

@Composable
private fun DetailScreen(
    modifier: Modifier = Modifier,
    uiState: DetailUiState,
    onFavoriteClick: (BrbaCharacter) -> Unit,
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (uiState) {
                is DetailUiState.Loading -> {
                    BrBaCircleProgress(modifier)
                }

                is DetailUiState.Success -> {
                    val character = uiState.character
                    LazyColumn {
                        item {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(character.img)
                                    .crossfade(true)
                                    .build(),
                                contentScale = ContentScale.Crop,
                                contentDescription = character.name,
                                alignment = Alignment.TopCenter,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f / 1.4f)
                            )
                        }
                        item {
                            Extra(
                                character = character,
                                clickFavorite = onFavoriteClick
                            )
                        }
                    }
                }

                is DetailUiState.Error -> {
                    uiState.exception?.printStackTrace()
                }
            }
        }
    }
}

@Composable
private fun Extra(
    character: BrbaCharacter,
    clickFavorite: (BrbaCharacter) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1.0f),
            )
            Spacer(modifier = Modifier.width(4.dp))
            BrbaIconFavorite(
                character.isFavorite,
                modifier = Modifier
                    .size(24.dp)
            ) { clickFavorite.invoke(character) }
        }
        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = character.nickname,
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.width(4.dp))

        val category = character.category
        if (category.isEmpty()) return
        if (category.contains(",")) {
            Chips(category.split(",").map { "#${it.trim()}" })
        } else {
            Chips(listOf("#$category"))
        }
    }
}

@Composable
private fun Chips(
    textList: List<String>,
    modifier: Modifier = Modifier
) {
    FlowRow(modifier = modifier.fillMaxWidth()) {
        textList.forEachIndexed { index, text ->
            if (index != 0) Spacer(modifier = modifier.width(4.dp))
            AssistChip(
                onClick = { },
                label = {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.tertiary

                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.onTertiary,
                ),
                modifier = Modifier.padding(0.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    DetailScreen(
        uiState = DetailUiState.Success(
            character = BrbaCharacter(
                charId = 0,
                name = "Walter White Walter White Walter White Walter White Walter White",
                birthday = "09-07-1958",
                img = "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
                status = "Presumed dead",
                nickname = "Heisenberg, Heisenberg, Heisenberg, Heisenberg, Heisenberg, Heisenberg",
                portrayed = "",
                category = "Breaking Bad1, Call Saul2, Breaking Bad3, Better Call Saul4, Breaking Bad5, Better Call Saul6",
                ratio = 1.5f,
                isFavorite = false,
                ctime = null
            )
        ),
        onFavoriteClick = {},
    )
}
