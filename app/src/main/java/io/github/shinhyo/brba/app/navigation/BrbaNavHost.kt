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
package io.github.shinhyo.brba.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.github.shinhyo.brba.feature.detail.navigaion.detailScreen
import io.github.shinhyo.brba.feature.detail.navigaion.navigateToDetail
import io.github.shinhyo.brba.feature.main.navigation.mainRoute
import io.github.shinhyo.brba.feature.main.navigation.mainScreen

@Composable
fun BrbaNavHost(startDestination: String = mainRoute) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        mainScreen { id ->
            navController.navigateToDetail(id)
        }
        detailScreen()
    }
}
