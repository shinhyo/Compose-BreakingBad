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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.core.theme.BrbaPreviewTheme
import io.github.shinhyo.brba.core.utils.brbaSharedElement

@Composable
fun SharedTransitionScope.BrbaCharacterCard(
    modifier: Modifier = Modifier,
    character: BrbaCharacter,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onCharacterClick: (BrbaCharacter) -> Unit,
    onFavoriteClick: (BrbaCharacter) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .aspectRatio(1f / character.ratio)
            .clickable { onCharacterClick.invoke(character) }
            .brbaSharedElement(
                isLocalInspectionMode = LocalInspectionMode.current,
                animatedVisibilityScope = animatedVisibilityScope,
                rememberSharedContentState(key = "character_${character.charId}_card"),
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(character.img)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize(),
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                0.6f to Color.Transparent,
                                1f to Color(0xA6000000),
                            ),
                        ),
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    BrbaIconFavorite(
                        enable = character.isFavorite,
                        modifier = Modifier,
                    ) { onFavoriteClick.invoke(character) }
                }

                Text(
                    text = character.name,
                    color = Color.White,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .padding(horizontal = 4.dp),
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    BrbaPreviewTheme {
        BrbaCharacterCard(
            modifier = Modifier,
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
                isFavorite = true,
                ctime = null,
            ),
            animatedVisibilityScope = it,
            onCharacterClick = {},
            onFavoriteClick = {},
        )
    }
}