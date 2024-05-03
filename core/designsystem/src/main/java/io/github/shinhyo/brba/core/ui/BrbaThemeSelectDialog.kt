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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import io.github.shinhyo.brba.core.model.BrbaThemeMode
import io.github.shinhyo.brba.core.theme.BrbaPreviewTheme

@Composable
fun BrbaThemeSelectDialog(
    themeMode: BrbaThemeMode,
    onDismissRequest: (() -> Unit)? = null,
    onConfirm: (BrbaThemeMode) -> Unit,
) {
    val typography = MaterialTheme.typography
    val colorScheme = MaterialTheme.colorScheme

    var mode by remember { mutableStateOf(themeMode) }

    AlertDialog(
        modifier = Modifier,
        properties = DialogProperties(),
        title = {
            Text(
                text = "Theme",
                style = typography.headlineSmall,
            )
        },
        text = {
            Column(Modifier.selectableGroup()) {
                BrbaThemeMode.entries.forEach {
                    ThemeRow(
                        text = it.name,
                        selected = it.name == mode.name,
                        onClick = { mode = it },
                    )
                }
            }
        },
        onDismissRequest = { onDismissRequest?.invoke() },
        confirmButton = {
            Text(
                text = stringResource(id = android.R.string.ok),
                style = typography.labelLarge,
                color = colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable(
                        onClick = {
                            onConfirm.invoke(mode)
                            onDismissRequest?.invoke()
                        },
                    ),
            )
        },
        dismissButton = {
            Text(
                text = stringResource(id = android.R.string.cancel),
                style = typography.labelLarge,
                color = colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable(onClick = { onDismissRequest?.invoke() }),
            )
        },
    )
}

@Composable
private fun ThemeRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    BrbaPreviewTheme {
        var themeMode by remember { mutableStateOf(BrbaThemeMode.Dark) }
        BrbaThemeSelectDialog(
            onDismissRequest = { },
            onConfirm = { themeMode = it },
            themeMode = themeMode,
        )
    }
}