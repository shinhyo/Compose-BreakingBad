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
package io.github.shinhyo.brba.feature.detail.navigaion

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.github.shinhyo.brba.feature.detail.DetailRoute

internal const val ARG_ID = "id"
const val DETAIL_ROUTE = "detail_route"

internal class DetailArgs(val characterId: Long) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        checkNotNull(savedStateHandle.get<Long>(ARG_ID))
    )
}

fun NavController.navigateToDetail(characterId: Long) {
    this.navigate("$DETAIL_ROUTE/$characterId")
}

fun NavGraphBuilder.detailScreen() {
    composable(
        route = "$DETAIL_ROUTE/{$ARG_ID}",
        arguments = listOf(
            navArgument(ARG_ID) { type = NavType.LongType },
        ),
    ) {
        DetailRoute()
    }
}
