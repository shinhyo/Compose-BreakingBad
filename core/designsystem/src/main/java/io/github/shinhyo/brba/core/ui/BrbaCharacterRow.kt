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

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.core.theme.BrbaPreviewTheme
import io.github.shinhyo.brba.core.utils.brbaSharedElement

@Composable
fun SharedTransitionScope.BrbaCharacterRow(
    modifier: Modifier = Modifier,
    character: BrbaCharacter,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onCharacterClick: (BrbaCharacter) -> Unit = {},
    onFavoriteClick: (BrbaCharacter) -> Unit = {},
) {
    Card(
        onClick = { onCharacterClick.invoke(character) },
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.img)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .size(120.dp)
                    .brbaSharedElement(
                        isLocalInspectionMode = LocalInspectionMode.current,
                        animatedVisibilityScope = animatedVisibilityScope,
                        rememberSharedContentState(key = "character_${character.charId}_row"),
                    ),
            )

            Spacer(modifier = Modifier.width(4.dp))

            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = Modifier
                        .fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = character.nickname,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraLight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth(),
                )

                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                        .fillMaxSize(),
                ) {
                    BrbaIconFavorite(
                        enable = character.isFavorite,
                        modifier = Modifier,
                    ) { onFavoriteClick.invoke(character) }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    BrbaPreviewTheme {
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
                ctime = null,
            ),
            animatedVisibilityScope = it,
        )
    }
}