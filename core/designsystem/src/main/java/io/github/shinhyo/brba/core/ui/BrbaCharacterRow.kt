/*
 * Copyright 2024 shinhyo
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
package io.github.shinhyo.brba.core.ui

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.github.shinhyo.brba.core.model.BrbaCharacter

@Composable
fun BrbaCharacterRow(
    modifier: Modifier = Modifier,
    character: BrbaCharacter,
    onCharacterClick: (Long) -> Unit = {},
    onFavoriteClick: (BrbaCharacter) -> Unit = {}
) {
    Card(onClick = {
        onCharacterClick.invoke(character.charId)
    }) {
        ConstraintLayout(
            modifier = modifier
                .height(120.dp)
                .fillMaxWidth()

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
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(name) {
                        start.linkTo(img.end, 8.dp)
                        top.linkTo(img.top, 8.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = character.nickname,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.ExtraLight,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(nickname) {
                        start.linkTo(name.start)
                        top.linkTo(name.bottom, 2.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            BrbaIconFavorite(
                enable = character.isFavorite,
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
    BrbaCharacterRow(
        character = BrbaCharacter(
            charId = 0,
            name = "Walter White Walter White Walter White",
            birthday = "09-07-1958",
            img = "https://~~~.jpg",
            status = "Presumed dead",
            nickname = "Heisenberg, Heisenberg, Heisenberg, Heisenberg",
            portrayed = "",
            category = "Breaking Bad",
            ratio = 1.5f,
            isFavorite = true,
            ctime = null
        )
    )
}