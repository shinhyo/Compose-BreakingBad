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
package io.github.shinhyo.brba.feature.bottombar

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.shinhyo.brba.core.ui.BrBaNavigationBar
import io.github.shinhyo.brba.core.ui.BrbaNavigationBarItem
import io.github.shinhyo.brba.feature.detail.navigaion.navigateToDetail
import io.github.shinhyo.brba.feature.favorate.navigation.favoriteComposable
import io.github.shinhyo.brba.feature.favorate.navigation.navigateFavorite
import io.github.shinhyo.brba.feature.main.navigation.LIST_ROUTE
import io.github.shinhyo.brba.feature.main.navigation.listComposable
import io.github.shinhyo.brba.feature.main.navigation.navigateList
import io.github.shinhyo.brba.feature.main.navigation.navigateSetting
import io.github.shinhyo.brba.feature.main.navigation.settingComposable

@Composable
fun SharedTransitionScope.BottomBarScreen(
    navController: NavHostController,
    navTabController: NavHostController = rememberNavController(),
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        bottomBar = {
            val currentRoute =
                navTabController.currentBackStackEntryAsState().value?.destination?.route
            BrBaNavigationBar(
                modifier = Modifier
                    .navigationBarsPadding(),
            ) {
                for (tab in remember { Tab.entries }) {
                    BrbaNavigationBarItem(
                        onSelected = { currentRoute == tab.route },
                        onClick = {
                            if (currentRoute == tab.route) return@BrbaNavigationBarItem
                            when (tab) {
                                Tab.LIST -> navTabController.navigateList()
                                Tab.FAVORITE -> navTabController.navigateFavorite()
                                Tab.SETTING -> navTabController.navigateSetting()
                            }
                        },
                        icon = {
                            Icon(
                                painterResource(id = tab.icon),
                                contentDescription = tab.label,
                            )
                        },
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        NavHost(
            navController = navTabController,
            startDestination = LIST_ROUTE,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            listComposable(
                navigateToDetail = { navController.navigateToDetail(character = it) },
                animatedVisibilityScope = animatedVisibilityScope,
            )
            favoriteComposable(
                onCharacterClick = { navController.navigateToDetail(character = it) },
                animatedVisibilityScope = animatedVisibilityScope,
            )
            settingComposable()
        }
    }
}