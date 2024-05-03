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
package io.github.shinhyo.brba.feature.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import io.github.shinhyo.brba.core.model.BrbaDeviceData
import io.github.shinhyo.brba.core.model.BrbaThemeMode
import io.github.shinhyo.brba.core.theme.BrbaPreviewTheme
import io.github.shinhyo.brba.core.ui.BrbaPreference
import io.github.shinhyo.brba.core.ui.BrbaThemeSelectDialog
import io.github.shinhyo.brba.core.ui.BrbaTopAppBar

@Composable
internal fun SettingRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val uiState: SettingUiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingScreen(
        modifier = modifier,
        uiState = uiState,
        onChangeThemeClick = viewModel::onChangeThemeClick,
    )
}

@Composable
private fun SettingScreen(
    modifier: Modifier = Modifier,
    uiState: SettingUiState,
    onChangeThemeClick: (BrbaThemeMode) -> Unit,
) {
    val hazeState: HazeState = remember { HazeState() }

    Scaffold(
        topBar = {
            BrbaTopAppBar(
                hazeState = hazeState,
                title = "Setting",
            )
        },
        modifier = modifier,
    ) {
        when (uiState) {
            is SettingUiState.Loading -> {}
            is SettingUiState.Success -> {
                val themeMode = uiState.settingData.deviceData.themeMode
                var showSettingsDialog by rememberSaveable { mutableStateOf(false) }

                if (showSettingsDialog) {
                    BrbaThemeSelectDialog(
                        themeMode = themeMode,
                        onDismissRequest = {
                            showSettingsDialog = false
                        },
                        onConfirm = { mode ->
                            onChangeThemeClick(mode)
                            showSettingsDialog = false
                        },
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                ) {
                    item {
                        BrbaPreference(
                            icon = {
                                Icon(
                                    painter = painterResource(id = io.github.shinhyo.brba.core.designsystem.R.drawable.ic_theme_light_dark),
                                    tint = MaterialTheme.colorScheme.tertiary,
                                    contentDescription = null,
                                )
                            },
                            title = { Text(text = "Theme") },
                            summary = { Text(text = themeMode.name) },
                            onClick = {
                                showSettingsDialog = true
                            },
                        )
                    }
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
        SettingScreen(
            uiState = SettingUiState.Success(
                SettingData(
                    deviceData = BrbaDeviceData(themeMode = BrbaThemeMode.Dark),
                ),
            ),
            onChangeThemeClick = {},
        )
    }
}