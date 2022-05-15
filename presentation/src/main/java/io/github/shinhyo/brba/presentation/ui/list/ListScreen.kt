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
package io.github.shinhyo.brba.presentation.ui.list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import io.github.shinhyo.brba.domain.model.Character
import io.github.shinhyo.brba.presentation.R
import io.github.shinhyo.brba.presentation.ui.common.IconFavorite
import kotlin.math.ceil

@Composable
fun ListScreen(
    viewModel: ListViewModel,
    select: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    val listScrollState = rememberScrollState()
    val clickFavorite: (Character) -> Unit = viewModel::upsertFavorite
    Body(viewModel, select, clickFavorite, listScrollState, modifier)
}

@Composable
private fun Body(
    viewModel: ListViewModel,
    select: (Character) -> Unit,
    clickFavorite: (Character) -> Unit,
    state: ScrollState,
    modifier: Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .verticalScroll(state)
            .statusBarsPadding()
    ) {
        Text(
            stringResource(R.string.brba),
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.h1,
            fontSize = 78.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        StaggeredVerticalGrid(
            maxColumnWidth = 160.dp,
            modifier = Modifier.padding(4.dp)
        ) {
            uiState.value.list.forEach {
                FeaturedList(
                    character = it, select = select,
                    clickFavorite = clickFavorite
                )
            }
        }
    }
}

@Composable
fun FeaturedList(
    character: Character,
    select: (Character) -> Unit,
    clickFavorite: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(4.dp),
//        color = MaterialTheme.colors.surface,
//        elevation = BrBaComposeTheme.elevations.card,
        shape = MaterialTheme.shapes.medium
    ) {
        ConstraintLayout(
            modifier = Modifier.clickable { select.invoke(character) }
        ) {
            val (image, name, dim, favorite) = createRefs()
            Image(
                painter = rememberImagePainter(
                    data = character.img,
                    builder = {
                        crossfade(true)
                    }
                ),
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
                            1f to Color(0x4d000000),
                        )
                    )
            ) { }

            IconFavorite(
                character.favorite,
                modifier = Modifier.constrainAs(favorite) {
                    top.linkTo(parent.top, margin = 4.dp)
                    start.linkTo(parent.start, margin = 4.dp)
                }
            ) { clickFavorite.invoke(character) }

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

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / columns
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(columns) { 0 } // track each column's height
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val colY = IntArray(columns) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                placeable.place(
                    x = columnWidth * column,
                    y = colY[column]
                )
                colY[column] += placeable.height
            }
        }
    }
}

private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}
