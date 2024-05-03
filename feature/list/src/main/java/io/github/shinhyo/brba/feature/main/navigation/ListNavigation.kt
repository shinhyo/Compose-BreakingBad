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
package io.github.shinhyo.brba.feature.main.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.feature.main.ListRoute

const val LIST_ROUTE = "list_route"

fun NavController.navigateList() {
    navigate(
        route = LIST_ROUTE,
        navOptions = navOptions {
            popUpTo(graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        },
    )
}

context(SharedTransitionScope)
fun NavGraphBuilder.listComposable(
    navigateToDetail: (BrbaCharacter) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    composable(
        route = LIST_ROUTE,
    ) {
        ListRoute(
            navigateToDetail = navigateToDetail,
            animatedVisibilityScope = animatedVisibilityScope,
        )
    }
}