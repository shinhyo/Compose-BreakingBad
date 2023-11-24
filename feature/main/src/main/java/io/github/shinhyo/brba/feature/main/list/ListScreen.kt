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
package io.github.shinhyo.brba.feature.main.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import io.github.shinhyo.brba.core.ui.StaggeredVerticalGrid
import io.github.shinhyo.brba.feature.main.R

@Composable
internal fun ListRoute(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel(),
    scrollState: LazyListState,
    navigateToDetail: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ListScreen(
        modifier = modifier,
        uiState = uiState,
        scrollState = scrollState,
        onCharacterClick = navigateToDetail,
        onFavoriteClick = viewModel::updateFavorite
    )
}

@Composable
private fun ListScreen(
    modifier: Modifier = Modifier,
    uiState: ListUiState,
    scrollState: LazyListState = rememberLazyListState(),
    onCharacterClick: (Long) -> Unit = {},
    onFavoriteClick: (BrbaCharacter) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState
    ) {
        item {
            Text(
                text = stringResource(R.string.brba),
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.h1,
                fontSize = 78.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding()
            )
        }
        item {
            when (uiState) {
                is ListUiState.Loading -> {}
                is ListUiState.Error -> {}
                is ListUiState.Success -> {
                    StaggeredVerticalGrid(
                        maxColumnWidth = 160.dp,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        uiState.list.forEach {
                            FeaturedList(
                                character = it,
                                onCharacterClick = onCharacterClick,
                                onFavoriteClick = onFavoriteClick
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FeaturedList(
    character: BrbaCharacter,
    onCharacterClick: (Long) -> Unit,
    onFavoriteClick: (BrbaCharacter) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(4.dp),
//        color = MaterialTheme.colors.surface,
//        elevation = BrBaComposeTheme.elevations.card,
        shape = MaterialTheme.shapes.medium
    ) {
        ConstraintLayout(
            modifier = Modifier.clickable { onCharacterClick.invoke(character.charId) }
        ) {
            val (image, name, dim, favorite) = createRefs()
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.img)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f / character.ratio)
                    .constrainAs(image) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top)
                    }
            )

            Box(
                modifier = Modifier
                    .constrainAs(dim) {
                        start.linkTo(image.start)
                        top.linkTo(image.top)
                        end.linkTo(image.end)
                        bottom.linkTo(image.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                    .background(
                        Brush.verticalGradient(
                            0.6f to Color.Transparent,
                            1f to Color(0x4d000000)
                        )
                    )
            ) { }

            IconFavorite(
                character.favorite,
                modifier = Modifier.constrainAs(favorite) {
                    top.linkTo(parent.top, margin = 4.dp)
                    start.linkTo(parent.start, margin = 4.dp)
                }
            ) { onFavoriteClick.invoke(character) }

            Text(
                text = character.name,
                color = Color.White,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .constrainAs(name) {
                        centerHorizontallyTo(parent)
                        bottom.linkTo(image.bottom, margin = 8.dp)
                    }

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ListScreen(
        uiState = ListUiState.Success(
            listOf(
                BrbaCharacter(
                    charId = 0,
                    name = "Walter White",
                    birthday = "09-07-1958",
                    img = "img\":\"https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
                    status = "Presumed dead",
                    nickname = "Heisenberg",
                    portrayed = "",
                    category = "Breaking Bad",
                    ratio = 1.2f,
                    favorite = true,
                    ctime = null
                ),
                BrbaCharacter(
                    charId = 0,
                    name = "Walter White",
                    birthday = "09-07-1958",
                    img = "img\":\"https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
                    status = "Presumed dead",
                    nickname = "Heisenberg",
                    portrayed = "",
                    category = "Breaking Bad",
                    ratio = 1.8f,
                    favorite = false,
                    ctime = null
                ),
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
                    favorite = false,
                    ctime = null
                )
            )
        )
    )
}