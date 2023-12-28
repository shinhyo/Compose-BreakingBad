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
package io.github.shinhyo.brba.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColor = darkColorScheme(
    primary = forestGreen,
    onPrimary = SeaGreen,
    secondary = forestGreen,
    background = Color.Black
)

// private val LightColor = lightColorScheme()

@Composable
fun BrBaComposeTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable
    () -> Unit
) {
    MaterialTheme(
        typography = Typography,
        shapes = Shapes,
        colorScheme = DarkColor,
        content = content
    )
}