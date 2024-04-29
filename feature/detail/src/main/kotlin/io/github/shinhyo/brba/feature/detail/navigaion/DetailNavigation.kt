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

import android.net.Uri
import androidx.compose.animation.SharedTransitionScope
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.feature.detail.DetailRoute

internal const val ARG_ID = "id"
internal const val ARG_IMAGE = "image"
internal const val ARG_FROM = "from"

const val DETAIL_ROUTE = "detail_route"

internal data class DetailArgs(
    val id: Long,
    val image: String,
    val from: String
) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        id = savedStateHandle.get<Long>(ARG_ID)!!,
        image = Uri.decode(savedStateHandle.get<String>(ARG_IMAGE))!!,
        from = Uri.decode(savedStateHandle.get<String>(ARG_FROM))!!
    )
}

fun NavController.navigateToDetail(character: BrbaCharacter, from: String) {
    this.navigate(
        route = "$DETAIL_ROUTE/${from}/${character.charId}/${Uri.encode(character.img)}"
    )
}

context(SharedTransitionScope)
fun NavGraphBuilder.detailComposable() {
    composable(
        route = "$DETAIL_ROUTE/{$ARG_FROM}/{$ARG_ID}/{$ARG_IMAGE}",
        arguments = listOf(
            navArgument(ARG_FROM) { type = NavType.StringType },
            navArgument(ARG_ID) { type = NavType.LongType },
            navArgument(ARG_IMAGE) { type = NavType.StringType },
        ),
    ) {
        DetailRoute(
            animatedVisibilityScope = this
        )
    }
}
