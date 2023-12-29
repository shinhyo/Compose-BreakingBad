/*
 * Copyright 2022 shinhyo
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
package io.github.shinhyo.brba.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import io.github.shinhyo.brba.app.ui.main.navigation.ROUTE_MAIN
import io.github.shinhyo.brba.app.ui.main.navigation.mainScreen
import io.github.shinhyo.brba.feature.detail.navigaion.detailScreen

@Composable
fun BrbaApp(
    appState: BrbaAppState = rememberAppState()
) {
    // root
    NavHost(
        navController = appState.navController,
        startDestination = ROUTE_MAIN,
        modifier = Modifier.fillMaxSize()
    ) {
        mainScreen(appState = appState)
        detailScreen()
    }
}