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

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import io.github.shinhyo.brba.core.designsystem.R

@Composable
fun BrbaTopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    hazeState: HazeState,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = title ?: LocalContext.current.getString(R.string.top_bar_title),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier,
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(Color.Transparent),
        modifier = modifier
            .padding(start = 0.dp)
            .hazeChild(
                hazeState,
                style = HazeDefaults.style(
                    blurRadius = 12.dp,
                    noiseFactor = 0.1f,
                ),
            )
            .fillMaxWidth(),
        actions = actions,
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    BrbaTopAppBar(
        title = "Title",
        hazeState = HazeState(),
    )
}