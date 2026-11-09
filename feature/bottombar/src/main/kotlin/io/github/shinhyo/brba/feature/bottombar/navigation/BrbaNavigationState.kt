/*
 * Copyright 2025 shinhyo
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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.runtime.serialization.NavKeySerializer
import androidx.savedstate.compose.serialization.serializers.MutableStateSerializer
import io.github.shinhyo.brba.feature.bottombar.Tab

/**
 * 탭 navigation state (Navigation 3).
 *
 * `when(tab)` 대신 Nav3 를 쓰는 이유: 탭별 스크롤 상태를 따로 보존하기 위해서다.
 * Nav2 로는 `navOptions(popUpTo/saveState/restoreState)` 로 흉내만 냈었다.
 *
 * "Multiple back stacks" 레시피 차용
 * (https://github.com/android/nav3-recipes/tree/main/app/src/main/java/com/example/nav3recipes/multiplestacks).
 */
class BrbaNavigationState(
    val startRoute: Tab,
    topLevelRoute: MutableState<Tab>,
    val backStacks: Map<Tab, NavBackStack<NavKey>>,
) {
    var topLevelRoute: Tab by topLevelRoute

    /** [startRoute] + (현재 다른 탭이면) 그 탭. 최대 2개. */
    val activeTabKeys: List<Tab>
        get() =
            if (topLevelRoute == startRoute) {
                listOf(startRoute)
            } else {
                listOf(startRoute, topLevelRoute)
            }

    /**
     * [rememberViewModelStoreNavEntryDecorator] 가 빠지면 entry content 와 자식 Composable 이
     * 서로 다른 `hiltViewModel()` 인스턴스를 받는다.
     */
    @Composable
    fun toEntries(
        entryProvider: (NavKey) -> NavEntry<NavKey>,
    ): List<NavEntry<NavKey>> {
        // mapValues 안에서 만들면 iteration 마다 새 scope 이라 remember 가 새 인스턴스를 만들 수 있다.
        val saveableDecorator = rememberSaveableStateHolderNavEntryDecorator<NavKey>()
        val viewModelDecorator = rememberViewModelStoreNavEntryDecorator<NavKey>()

        val decoratedEntries =
            backStacks.mapValues { (_, stack) ->
                rememberDecoratedNavEntries(
                    backStack = stack,
                    entryDecorators = listOf(saveableDecorator, viewModelDecorator),
                    entryProvider = entryProvider,
                )
            }
        return activeTabKeys.flatMap { decoratedEntries[it] ?: emptyList() }
    }
}

@Composable
fun rememberBrbaNavigationState(
    startRoute: Tab = Tab.LIST,
    topLevelRoutes: Set<Tab> = Tab.entries.toSet(),
): BrbaNavigationState {
    val topLevelRoute =
        rememberSerializable(
            startRoute,
            topLevelRoutes,
            serializer = MutableStateSerializer(NavKeySerializer()),
        ) {
            mutableStateOf(startRoute)
        }

    val backStacks =
        topLevelRoutes.associateWith { key ->
            rememberNavBackStack(key)
        }

    return remember(startRoute, topLevelRoutes) {
        BrbaNavigationState(
            startRoute = startRoute,
            topLevelRoute = topLevelRoute,
            backStacks = backStacks,
        )
    }
}

/** back stack 은 각 탭이 보유해 `topLevelRoute` 만 바꾼다. */
fun BrbaNavigationState.navigateToTab(route: Tab) {
    if (route == topLevelRoute) return
    topLevelRoute = route
}

fun BrbaNavigationState.push(route: NavKey) {
    backStacks[topLevelRoute]?.add(route)
}

/** backStack 들은 saveable 이라 그냥 두면 열려 있던 디테일이 재실행 후에도 복원된다. */
fun BrbaNavigationState.resetToRoots() {
    backStacks.values.forEach { stack ->
        while (stack.size > 1) stack.removeAt(stack.lastIndex)
    }
    topLevelRoute = startRoute
}

/** sub-route pop → start 탭이 아니면 start 탭으로 → 아니면 false(호출부가 Activity 종료). */
fun BrbaNavigationState.goBack(): Boolean {
    val stack = backStacks[topLevelRoute]
    if (stack != null && stack.size > 1) {
        stack.removeLastOrNull()
        return true
    }
    if (topLevelRoute == startRoute) return false
    topLevelRoute = startRoute
    return true
}