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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.core.ui.IconFavorite
import io.github.shinhyo.brba.core.ui.ProgressScreen
import io.github.shinhyo.brba.feature.list.R

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
    when (uiState) {
        is ListUiState.Loading -> {
            ProgressScreen(modifier)
        }

        is ListUiState.Error -> {
            uiState.exception?.printStackTrace()
        }

        is ListUiState.Success -> {
            LazyVerticalStaggeredGrid(
                modifier = modifier,
                state = rememberLazyStaggeredGridState(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalItemSpacing = 8.dp,
                columns = StaggeredGridCells.Adaptive(120.dp)
            ) {
                item(
                    span = StaggeredGridItemSpan.FullLine
                ) {
                    Header()
                }
                items(items = uiState.list, key = { it.charId }) {
                    CharacterCard(
                        character = it,
                        onCharacterClick = onCharacterClick,
                        onFavoriteClick = onFavoriteClick
                    )
                }
            }
        }
    }
}

@Composable
fun Header() {
    Text(
        text = stringResource(R.string.brba),
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.headlineLarge,
        fontSize = 78.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    )
}

@Composable
fun CharacterCard(
    character: BrbaCharacter,
    onCharacterClick: (Long) -> Unit,
    onFavoriteClick: (BrbaCharacter) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1f / character.ratio)
            .clickable { onCharacterClick.invoke(character.charId) }
    ) {
        ConstraintLayout() {
            val (image, name, dim, favorite) = createRefs()
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(character.img)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Box(
                modifier = Modifier
                    .constrainAs(dim) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                    .background(
                        Brush.verticalGradient(
                            0.6f to Color.Transparent,
                            1f to Color(0xA6000000)
                        )
                    )
            )

            IconFavorite(
                character.favorite,
                modifier = Modifier
                    .constrainAs(favorite) {
                        top.linkTo(parent.top, margin = 4.dp)
                        start.linkTo(parent.start, margin = 4.dp)
                    }
            ) { onFavoriteClick.invoke(character) }

            Text(
                text = character.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .constrainAs(name) {
                        centerHorizontallyTo(parent)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                    }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCharacterCard() {
    CharacterCard(
        character = BrbaCharacter(
            charId = 0,
            name = "Walter White",
            birthday = "09-07-1958",
            img = "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
            status = "Presumed dead",
            nickname = "Heisenberg",
            portrayed = "",
            category = "Breaking Bad",
            ratio = 1.2f,
            favorite = true,
            ctime = null
        ),
        onCharacterClick = {},
        onFavoriteClick = {},
        modifier = Modifier
    )
}

@Preview(showBackground = false)
@Composable
private fun Preview() {
    ListScreen(
        uiState = ListUiState.Success(
            listOf(
                BrbaCharacter(
                    charId = 0,
                    name = "Walter White",
                    birthday = "09-07-1958",
                    img = "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
                    status = "Presumed dead",
                    nickname = "Heisenberg",
                    portrayed = "",
                    category = "Breaking Bad",
                    ratio = 1.2f,
                    favorite = true,
                    ctime = null
                ),
                BrbaCharacter(
                    charId = 1,
                    name = "Walter White",
                    birthday = "09-07-1958",
                    img = "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
                    status = "Presumed dead",
                    nickname = "Heisenberg",
                    portrayed = "",
                    category = "Breaking Bad",
                    ratio = 1.8f,
                    favorite = false,
                    ctime = null
                ),
                BrbaCharacter(
                    charId = 2,
                    name = "Walter White",
                    birthday = "09-07-1958",
                    img = "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
                    status = "Presumed dead",
                    nickname = "Heisenberg",
                    portrayed = "",
                    category = "Breaking Bad",
                    ratio = 1.5f,
                    favorite = false,
                    ctime = null
                ),
                BrbaCharacter(
                    charId = 3,
                    name = "Walter White",
                    birthday = "09-07-1958",
                    img = "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
                    status = "Presumed dead",
                    nickname = "Heisenberg",
                    portrayed = "",
                    category = "Breaking Bad",
                    ratio = 1f,
                    favorite = false,
                    ctime = null
                )
            )
        )
    )
}