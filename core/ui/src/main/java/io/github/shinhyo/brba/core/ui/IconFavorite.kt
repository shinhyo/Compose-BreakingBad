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
package io.github.shinhyo.brba.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IconFavorite(
    enable: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Icon(
        imageVector = if (enable) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
        tint = if (enable) Color(0xffBE2724) else Color.DarkGray,
        contentDescription = null,
        modifier = modifier
            .size(24.dp)
            .clickable(
                onClick = onClick,
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = false)
            )
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    IconFavorite(
        enable = false,
        modifier = Modifier
    ) {
    }
}