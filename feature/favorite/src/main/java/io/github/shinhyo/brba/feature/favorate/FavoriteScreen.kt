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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.core.ui.IconFavorite
import io.github.shinhyo.brba.core.ui.ProgressScreen
import io.github.shinhyo.brba.feature.favorite.R

@Composable
fun FavoriteRoute(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
    navigateToDetail: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoriteScreen(
        modifier = modifier,
        uiState = uiState,
        onCharacterClick = navigateToDetail,
        onFavoriteClick = viewModel::updateFavorite
    )
}

@Composable
private fun FavoriteScreen(
    modifier: Modifier = Modifier,
    uiState: FavoriteUiState,
    onCharacterClick: (Long) -> Unit,
    onFavoriteClick: (BrbaCharacter) -> Unit = {}
) {
    when (uiState) {
        is FavoriteUiState.Loading -> {
            ProgressScreen(modifier.fillMaxSize())
        }

        is FavoriteUiState.Success -> ListScreen(
            modifier = modifier.fillMaxSize(),
            state = rememberLazyListState(),
            list = uiState.list,
            onCharacterClick = onCharacterClick,
            onFavoriteClick = onFavoriteClick
        )

        is FavoriteUiState.Empty -> EmptyScreen()
        is FavoriteUiState.Error -> {
            uiState.exception?.printStackTrace()
        }
    }
}

@Composable
private fun ListScreen(
    modifier: Modifier,
    list: List<BrbaCharacter>,
    state: LazyListState,
    onCharacterClick: (Long) -> Unit,
    onFavoriteClick: (BrbaCharacter) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 0.dp,
            end = 16.dp,
            bottom = 0.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = list,
            key = { _, item -> item.charId }
        ) { index, item ->
            CharacterCard(
                modifier = Modifier
                    .run {
                        if (index == 0) statusBarsPadding() else this
                    },
                character = item,
                onCharacterClick = onCharacterClick,
                onFavoriteClick = onFavoriteClick
            )
        }

//        items(
//            items = list,
//            key = { it.charId }
//        ) {
//            CharacterCard(
//                character = it,
//                onCharacterClick = onCharacterClick,
//                onFavoriteClick = onFavoriteClick
//            )
//        }
    }
}

@Composable
private fun EmptyScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Icon(
            painterResource(id = R.drawable.ic_flask_outline),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(50.dp)
        )
        Text(
            stringResource(R.string.empty),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun CharacterCard(
    modifier: Modifier,
    character: BrbaCharacter,
    onCharacterClick: (Long) -> Unit = {},
    onFavoriteClick: (BrbaCharacter) -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
            .height(120.dp)
            .fillMaxWidth()
            .clickable { onCharacterClick.invoke(character.charId) }
    ) {
        val (img, name, nickname, favorite) = createRefs()
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.img)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .aspectRatio(1f)
                .constrainAs(img) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clip(MaterialTheme.shapes.medium)
        )

        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineSmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(name) {
                    start.linkTo(img.end, 8.dp)
                    top.linkTo(img.top)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = character.nickname,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.ExtraLight,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(nickname) {
                    start.linkTo(name.start)
                    top.linkTo(name.bottom, 4.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        IconFavorite(
            enable = character.favorite,
            modifier = Modifier.constrainAs(favorite) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        ) { onFavoriteClick.invoke(character) }
    }
//    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
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
                    favorite = true,
                    ctime = null
                )
            )
        ),
        onCharacterClick = {}
    )
}