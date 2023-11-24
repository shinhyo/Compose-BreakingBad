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
package io.github.shinhyo.brba.feature.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import io.github.shinhyo.brba.feature.main.favorite.FavoriteRoute
import io.github.shinhyo.brba.feature.main.list.ListRoute

private enum class BottomNavTabs(val label: String, val icon: Int) {
    LIST("Character", R.drawable.ic_account_cowboy_hat),
    FAVORITE("Favorite", R.drawable.ic_heart)
}

@Composable
fun MainScreen(
    navigateToDetail: (Long) -> Unit
) {
    val selectedTab: MutableState<BottomNavTabs> = rememberSaveable {
        mutableStateOf(BottomNavTabs.LIST)
    }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.background,
                modifier = Modifier.navigationBarsPadding()
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
                        selectedContentColor = MaterialTheme.colors.primaryVariant
                    )
                }
            }
        }
    ) {
        val modifier = Modifier.padding(it)
        val listScrollState = rememberLazyListState()
        val favoriteScrollState = rememberLazyListState()

        when (selectedTab.value) {
            BottomNavTabs.LIST -> ListRoute(
                scrollState = listScrollState,
                modifier = modifier,
                navigateToDetail = navigateToDetail
            )
            BottomNavTabs.FAVORITE -> FavoriteRoute(
                scrollState = favoriteScrollState,
                modifier = modifier,
                navigateToDetail = navigateToDetail
            )
        }
    }

    BackHandler(
        enabled = selectedTab.value != BottomNavTabs.LIST,
        onBack = { selectedTab.value = BottomNavTabs.LIST }
    )
}