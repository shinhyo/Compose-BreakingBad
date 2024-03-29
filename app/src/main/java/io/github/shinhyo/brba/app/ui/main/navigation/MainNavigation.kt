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
package io.github.shinhyo.brba.app.ui.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.shinhyo.brba.app.ui.BrbaAppState
import io.github.shinhyo.brba.app.ui.main.MainScreen

const val ROUTE_MAIN = "route_main"

fun NavGraphBuilder.mainScreen(
    appState: BrbaAppState
) {
    composable(
        route = ROUTE_MAIN,
    ) {
        MainScreen(appState)
    }
}