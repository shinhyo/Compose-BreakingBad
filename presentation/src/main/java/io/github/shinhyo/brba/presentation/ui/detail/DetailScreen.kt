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
package io.github.shinhyo.brba.presentation.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.glide.rememberGlidePainter
import io.github.shinhyo.brba.domain.model.Character
import io.github.shinhyo.brba.presentation.ui.common.IconFavorite

@Composable
fun DetailScreen(viewModel: DetailViewModel) {
    val character = viewModel.character.collectAsState()
    Body(character = character.value, clickFavorite = viewModel::upsertFavorite)
}

@Composable
private fun Body(character: Character, clickFavorite: (Character) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LazyColumn {
            item {

                Image(
                    painter = rememberGlidePainter(
                        request = character.img,
                        fadeIn = true,
                    ),
                    contentDescription = character.name,
                    alignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f / 1.4f)
                )
            }
            item {
                Extra(character, clickFavorite)
            }
        }
    }
}

@Composable
private fun Extra(
    character: Character,
    clickFavorite: (Character) -> Unit
) {
    ConstraintLayout(modifier = Modifier.padding(16.dp)) {
        val (name, favorite, nick, status, category) = createRefs()
        Text(
            text = character.name,
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                width = Dimension.fillToConstraints
            }
        )

        IconFavorite(
            character.favorite,
            modifier = Modifier.constrainAs(favorite) {
                start.linkTo(name.end, margin = 8.dp)
                top.linkTo(name.top)
                bottom.linkTo(name.bottom)
            }
        ) { clickFavorite.invoke(character) }

        Text(
            text = character.nickname,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Thin,
            modifier = Modifier.constrainAs(nick) {
                start.linkTo(name.start)
                top.linkTo(name.bottom)
            }
        )
        GetCategoryText(
            character = character,
            modifier = Modifier
                .constrainAs(category) {
                    start.linkTo(name.start)
                    top.linkTo(nick.bottom, 8.dp)
                }
        )
    }
}

@Composable
private fun GetCategoryText(character: Character, modifier: Modifier) {
    @Composable
    fun Chips(textList: List<String>, modifier: Modifier) {
        Row(modifier = modifier.fillMaxWidth()) {
            textList.forEachIndexed { index, text ->
                if (index != 0) Spacer(modifier = modifier.padding(start = 4.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Light,
                    color = Color.Black,
                    modifier = Modifier
                        .background(MaterialTheme.colors.primaryVariant, RoundedCornerShape(8.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
        }
    }

    val category = character.category
    if (category.isEmpty()) return
    if (category.contains(",")) {
        Chips(category.split(",").map { "#$it" }, modifier)
    } else {
        Chips(listOf("#$category"), modifier)
    }
}
