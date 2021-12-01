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
package io.github.shinhyo.brba.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.google.accompanist.insets.navigationBarsPadding
import io.github.shinhyo.brba.domain.model.Character
import io.github.shinhyo.brba.presentation.R
import io.github.shinhyo.brba.presentation.ui.detail.DetailScreen
import io.github.shinhyo.brba.presentation.ui.favorite.FavoriteScreen
import io.github.shinhyo.brba.presentation.ui.list.ListScreen
import io.github.shinhyo.brba.presentation.utils.BackHandler

sealed class NavScreens(val route: String) {
    object MAIN : NavScreens("main")
    object DETAIL : NavScreens("detail")
}

@Composable
fun NavGraph(startDestination: NavScreens = NavScreens.MAIN) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }
    val selectedTab = remember { mutableStateOf(BottomNavTabs.LIST) }
    NavHost(navController = navController, startDestination = startDestination.route) {
        composable(
            route = NavScreens.MAIN.route
        ) { NavScreen(actions = actions, selectedTab) }
        composable(
            route = "${NavScreens.DETAIL.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { DetailScreen(viewModel = hiltViewModel()) }
    }
}

// NavTabs
enum class BottomNavTabs(val label: String, val icon: Int) {
    LIST("Character", R.drawable.ic_account_cowboy_hat),
    FAVORITE("Favorite", R.drawable.ic_heart),
}

@Composable
fun NavScreen(
    actions: MainActions,
    selectedTab: MutableState<BottomNavTabs>
) {
    Scaffold(
        bottomBar = {
            BottomNavigation(
                modifier = Modifier.navigationBarsPadding(),
                backgroundColor = MaterialTheme.colors.background
            ) {
                for (tab in BottomNavTabs.values()) {
                    BottomNavigationItem(
                        selected = selectedTab.value == tab,
                        onClick = {
                            if (selectedTab.value == tab) return@BottomNavigationItem
                            selectedTab.value = tab
                        },
                        icon = {
                            Icon(
                                painterResource(id = tab.icon),
                                contentDescription = tab.label
                            )
                        },
                        unselectedContentColor = Color.LightGray,
                        selectedContentColor = MaterialTheme.colors.primaryVariant,
                    )
                }
            }
        },
    ) {
        val modifier = Modifier.padding(it)
        when (selectedTab.value) {
            BottomNavTabs.LIST -> ListScreen(
                hiltViewModel(),
                actions.moveDetail,
                modifier
            )
            BottomNavTabs.FAVORITE -> FavoriteScreen(
                hiltViewModel(),
                actions.moveDetail,
                modifier
            )
        }
    }
//    }
    BackHandler(
        enabled = selectedTab.value != BottomNavTabs.LIST,
        onBack = { selectedTab.value = BottomNavTabs.LIST }
    )
}

class MainActions(navController: NavHostController) {

    val moveDetail: (Character) -> Unit = { character ->
        navController.navigate("${NavScreens.DETAIL.route}/${character.charId}")
    }
}
