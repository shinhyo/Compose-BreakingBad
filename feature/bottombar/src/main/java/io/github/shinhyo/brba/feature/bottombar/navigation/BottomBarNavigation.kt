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
package io.github.shinhyo.brba.feature.bottombar.navigation

import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import io.github.shinhyo.brba.feature.bottombar.BottomBarScreen

const val ROUTE_MAIN = "route_main"

context(SharedTransitionScope)
fun NavGraphBuilder.bottomBardComposable(
    navController: NavHostController,
) {
    composable(
        route = ROUTE_MAIN,
    ) {
        BottomBarScreen(
            navController = navController,
            animatedVisibilityScope = this,
        )
    }
}