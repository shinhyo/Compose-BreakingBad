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
package io.github.shinhyo.brba.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.github.shinhyo.brba.core.model.BrbaThemeMode
import io.github.shinhyo.brba.core.theme.BrBaTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainUiState by mutableStateOf(MainUiState.Loading)

        viewModel.uiState
            .onEach { state ->
                when (state) {
                    is MainUiState.Loading -> {
                        splashScreen.setKeepOnScreenCondition { true }
                    }

                    is MainUiState.Success -> {
                        uiState = state
                        splashScreen.setKeepOnScreenCondition { false }
                    }
                }
            }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)

        setContent {
            when (val sate = uiState) {
                is MainUiState.Loading -> {}
                is MainUiState.Success -> {
                    BrBaTheme(
                        darkTheme = when (sate.deviceData.themeMode) {
                            BrbaThemeMode.Light -> false
                            BrbaThemeMode.Dark -> true
                            BrbaThemeMode.System -> isSystemInDarkTheme()
                        },
                        content = {
                            BrbaApp()
                        },
                    )
                }
            }
        }
    }
}