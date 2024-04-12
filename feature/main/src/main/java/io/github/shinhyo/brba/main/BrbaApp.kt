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
package io.github.shinhyo.brba.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import io.github.shinhyo.brba.feature.bottombar.navigation.ROUTE_MAIN
import io.github.shinhyo.brba.feature.bottombar.navigation.bottomBarScreen
import io.github.shinhyo.brba.feature.detail.navigaion.detailScreen

@Composable
fun BrbaApp(
    appState: BrbaAppState = rememberAppState()
) {
    BrbaNavGraph(appState)
}

@Composable
private fun BrbaNavGraph(appState: BrbaAppState) {
    NavHost(
        navController = appState.navController,
        startDestination = ROUTE_MAIN,
        modifier = Modifier.fillMaxSize()
    ) {
        bottomBarScreen(
            navController = appState.navController
        )
        detailScreen()
    }
}