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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.shinhyo.brba.app.ui.main.Tab
import io.github.shinhyo.brba.feature.favorate.navigation.navigateFavorite
import io.github.shinhyo.brba.feature.main.navigation.navigateList
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    navTabController: NavHostController = rememberNavController()
): BrbaAppState = remember(
    navController,
    navTabController,
    coroutineScope
) {
    BrbaAppState(coroutineScope, navController, navTabController)
}

@Stable
class BrbaAppState(
    val coroutineScope: CoroutineScope,
    val navController: NavHostController,
    val navTabController: NavHostController
) {

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTabDestination: NavDestination?
        @Composable get() = navTabController.currentBackStackEntryAsState().value?.destination

    fun navigateTopDestination(destination: Tab) {
        when (destination) {
            Tab.LIST -> navTabController.navigateList()
            Tab.FAVORITE -> navTabController.navigateFavorite()
        }
    }
}