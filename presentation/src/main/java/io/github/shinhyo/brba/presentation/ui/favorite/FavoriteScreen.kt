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
package io.github.shinhyo.brba.presentation.ui.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import io.github.shinhyo.brba.domain.model.Character
import io.github.shinhyo.brba.presentation.R
import io.github.shinhyo.brba.presentation.ui.common.IconFavorite

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    select: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    val list = viewModel.list.collectAsState()
    if (list.value.isEmpty()) {
        EmptyScreen()
    } else {
        ListScreen(modifier, list, select, viewModel)
    }
}

@Composable
private fun ListScreen(
    modifier: Modifier,
    list: State<List<Character>>,
    select: (Character) -> Unit,
    viewModel: FavoriteViewModel
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 8.dp, top = 32.dp, end = 8.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(list.value) {
            ItemContent(
                item = it,
                click = select,
                clickFavorite = viewModel::upsertFavorite
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
    item: Character,
    click: (Character) -> Unit,
    clickFavorite: (Character) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { click.invoke(item) }
            .background(Color(0x4d000000))
    ) {

        ConstraintLayout(
            modifier = Modifier

        ) {
            val (img, name, nickname, favorite) = createRefs()
            Image(
                painter = rememberImagePainter(
                    data = item.img,
                    builder = {
                        crossfade(true)
                    }
                ),
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
                text = item.name,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(name) {
                    start.linkTo(img.end, 8.dp)
                    top.linkTo(img.top, 4.dp)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = item.nickname,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier.constrainAs(nickname) {
                    start.linkTo(name.start)
                    top.linkTo(name.bottom, 4.dp)
                    width = Dimension.fillToConstraints
                }
            )

            IconFavorite(
                item.favorite,
                modifier = Modifier.constrainAs(favorite) {
                    end.linkTo(parent.end, 8.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                }
            ) { clickFavorite.invoke(item) }
        }
    }
}
