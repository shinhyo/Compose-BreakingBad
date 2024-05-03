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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.shinhyo.brba.core.theme.BrbaPreviewTheme

@Composable
fun BrbaPreference(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable () -> Unit,
    enabled: Boolean = true,
    summary: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = modifier.then(
            if (onClick != null) {
                Modifier.clickable(enabled, onClick = onClick)
            } else {
                Modifier
            },
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon?.let {
            Box(
                modifier =
                Modifier
                    .widthIn(min = 56.dp)
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 0.dp,
                        bottom = 16.dp,
                    ),
                contentAlignment = Alignment.CenterStart,
            ) {
                CompositionLocalProvider(
                    LocalContentColor.provides(
                        if (enabled) {
                            colorScheme.onSurfaceVariant
                        } else {
                            colorScheme.onSurfaceVariant.copy(0.38f)
                        },
                    ),
                    content = icon,
                )
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .padding(
                        start = if (icon != null) 0.dp else 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                    ),
            ) {
                CompositionLocalProvider(
                    LocalContentColor.provides(
                        if (enabled) {
                            colorScheme.onSurface
                        } else {
                            colorScheme.onSurface.copy(alpha = 0.38f)
                        },
                    ),
                ) {
                    ProvideTextStyle(value = typography.bodyLarge, content = title)
                    summary?.let {
                        ProvideTextStyle(
                            value = typography.bodyMedium,
                            content = summary,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BrbaPreviewTheme {
        BrbaPreference(
            enabled = true,
            title = {
                Text(text = "title")
            },
            summary = {
                Text(text = "summary")
            },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.List,
                    contentDescription = null,
                )
            },
            onClick = {},
        )
    }
}