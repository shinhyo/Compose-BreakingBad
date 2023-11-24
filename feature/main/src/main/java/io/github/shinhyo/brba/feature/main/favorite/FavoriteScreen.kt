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
package io.github.shinhyo.brba.feature.main.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import io.github.shinhyo.brba.feature.main.R

@Composable
fun FavoriteRoute(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
    scrollState: LazyListState,
    navigateToDetail: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoriteScreen(
        modifier = modifier,
        uiState = uiState,
        scrollState = scrollState,
        onCharacterClick = navigateToDetail,
        onFavoriteClick = viewModel::updateFavorite
    )
}

@Composable
private fun FavoriteScreen(
    modifier: Modifier = Modifier,
    uiState: FavoriteUiState,
    scrollState: LazyListState = LazyListState(),
    onCharacterClick: (Long) -> Unit,
    onFavoriteClick: (BrbaCharacter) -> Unit = {}
) {
    when (uiState) {
        is FavoriteUiState.Loading -> {}
        is FavoriteUiState.Success -> ListScreen(
            modifier = modifier,
            state = scrollState,
            list = uiState.list,
            onCharacterClick = onCharacterClick,
            onFavoriteClick = onFavoriteClick
        )

        is FavoriteUiState.Empty -> EmptyScreen()
        is FavoriteUiState.Error -> {}
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
            start = 8.dp,
            top = 32.dp,
            end = 8.dp,
            bottom = 32.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = list,
            key = { it.charId }
        ) {
            ItemContent(
                character = it,
                onCharacterClick = onCharacterClick,
                onFavoriteClick = onFavoriteClick
            )
        }
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
            tint = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.size(50.dp)
        )
        Text(
            stringResource(R.string.empty),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun ItemContent(
    character: BrbaCharacter,
    onCharacterClick: (Long) -> Unit = {},
    onFavoriteClick: (BrbaCharacter) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onCharacterClick.invoke(character.charId) }
            .background(Color(0x4d000000))
    ) {
        ConstraintLayout(
            modifier = Modifier

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
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(name) {
                    start.linkTo(img.end, 8.dp)
                    top.linkTo(img.top, 4.dp)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = character.nickname,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier.constrainAs(nickname) {
                    start.linkTo(name.start)
                    top.linkTo(name.bottom, 4.dp)
                    width = Dimension.fillToConstraints
                }
            )

            IconFavorite(
                character.favorite,
                modifier = Modifier.constrainAs(favorite) {
                    end.linkTo(parent.end, 8.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                }
            ) { onFavoriteClick.invoke(character) }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    FavoriteScreen(
        uiState = FavoriteUiState.Success(
            list = listOf(
                BrbaCharacter(
                    charId = 0,
                    name = "Walter White",
                    birthday = "09-07-1958",

                    img = "img\":\"https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
                    status = "Presumed dead",
                    nickname = "Heisenberg",
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