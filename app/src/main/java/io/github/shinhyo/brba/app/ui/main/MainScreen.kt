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
package io.github.shinhyo.brba.app.ui.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import io.github.shinhyo.brba.app.ui.BrbaAppState
import io.github.shinhyo.brba.feature.detail.navigaion.navigateToDetail
import io.github.shinhyo.brba.feature.favorate.navigation.favoriteScreen
import io.github.shinhyo.brba.feature.main.navigation.LIST_ROUTE
import io.github.shinhyo.brba.feature.main.navigation.listScreen

@Composable
fun MainScreen(appState: BrbaAppState) {
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        bottomBar = { BrBaNavigationBar(appState) }
    ) { paddingValues ->
        val navController: NavHostController = appState.navController
        val navTabController: NavHostController = appState.navTabController
        NavHost(
            navController = navTabController,
            startDestination = LIST_ROUTE,
            modifier = Modifier.padding(paddingValues)
        ) {
            listScreen(navController::navigateToDetail)
            favoriteScreen(navController::navigateToDetail)
        }
    }
}

@Composable
fun BrBaNavigationBar(appState: BrbaAppState) {
    val currentTabRoute = appState.currentTabDestination?.route
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .navigationBarsPadding()
    ) {
        for (tab in Tab.entries) {
            NavigationBarItem(
                selected = currentTabRoute == tab.route,
                onClick = { appState.navigateTopDestination(tab) },
                icon = {
                    Icon(
                        painterResource(id = tab.icon),
                        contentDescription = tab.label
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = Color.LightGray
                )
            )
        }
    }
}