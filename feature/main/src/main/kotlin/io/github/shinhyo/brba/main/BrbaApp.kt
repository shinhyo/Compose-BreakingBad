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
package io.github.shinhyo.brba.main

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.metadata
import androidx.navigation3.scene.SinglePaneSceneStrategy
import androidx.navigation3.ui.NavDisplay
import io.github.shinhyo.brba.core.model.BrbaCharacter
import io.github.shinhyo.brba.feature.bottombar.BrbaTabScaffold
import io.github.shinhyo.brba.feature.bottombar.Tab
import io.github.shinhyo.brba.feature.bottombar.navigation.BrbaNavigationState
import io.github.shinhyo.brba.feature.bottombar.navigation.goBack
import io.github.shinhyo.brba.feature.bottombar.navigation.navigateToTab
import io.github.shinhyo.brba.feature.bottombar.navigation.push
import io.github.shinhyo.brba.feature.bottombar.navigation.rememberBrbaNavigationState
import io.github.shinhyo.brba.feature.detail.DetailRoute
import io.github.shinhyo.brba.feature.detail.navigaion.Detail
import io.github.shinhyo.brba.feature.detail.navigaion.DetailEntryMarker
import io.github.shinhyo.brba.feature.detail.navigaion.DetailOverlaySceneStrategy
import io.github.shinhyo.brba.feature.detail.navigaion.navAnimatedVisibilityScope
import io.github.shinhyo.brba.feature.favorate.FavoriteRoute
import io.github.shinhyo.brba.feature.list.ListRoute
import io.github.shinhyo.brba.feature.setting.SettingRoute

/**
 * 앱 진입 — 3개 탭을 Navigation 3 로 호스팅하고, 디테일은 오버레이로 띄운다.
 *
 * - `SharedTransitionLayout` 안에서 [NavDisplay] 를 그려야 카드와 디테일 공유 요소 전환이 된다.
 * - 탭 전환은 `topLevelRoute` 만 바꾸고, 각 탭의 back stack 은 따로 유지된다.
 * - 디테일은 [DetailOverlaySceneStrategy] 가 `OverlayScene` 으로 띄운다 —
 *   아래 탭 화면이 합성 상태로 남아 리스트 스크롤이 유지된다.
 *
 * 탭 entry 는 `entry<NavKey>(Tab.LIST)` 처럼 키를 값으로 넘긴다.
 * `entry<Tab.LIST>` 는 enum entry 를 타입으로 쓰게 돼 컴파일되지 않고,
 * KClass 당 provider 하나 제한 때문에 탭마다 따로 등록할 수도 없다.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BrbaApp(
    navState: BrbaNavigationState = rememberBrbaNavigationState(),
) {
    SharedTransitionLayout {
        // 공유 요소 전환은 `SharedTransitionScope` 가 필요하다.
        // nav3 entry 안에서는 receiver 가 없어지므로 여기서 받아 둔다.
        val sharedTransitionScope = this
        val onSelectTab: (Tab) -> Unit = { tab -> navState.navigateToTab(tab) }
        val openDetail: (BrbaCharacter) -> Unit = { character ->
            navState.push(character.toDetail())
        }

        NavDisplay(
            entries =
            navState.toEntries(
                entryProvider {
                    entry<NavKey>(Tab.LIST) {
                        BrbaTabScaffold(
                            currentTab = navState.topLevelRoute,
                            onSelectTab = onSelectTab,
                        ) {
                            with(sharedTransitionScope) {
                                ListRoute(
                                    animatedVisibilityScope = navAnimatedVisibilityScope(),
                                    navigateToDetail = openDetail,
                                )
                            }
                        }
                    }
                    entry<NavKey>(Tab.FAVORITE) {
                        BrbaTabScaffold(
                            currentTab = navState.topLevelRoute,
                            onSelectTab = onSelectTab,
                        ) {
                            with(sharedTransitionScope) {
                                FavoriteRoute(
                                    animatedVisibilityScope = navAnimatedVisibilityScope(),
                                    onCharacterClick = openDetail,
                                )
                            }
                        }
                    }
                    entry<NavKey>(Tab.SETTING) {
                        BrbaTabScaffold(
                            currentTab = navState.topLevelRoute,
                            onSelectTab = onSelectTab,
                        ) {
                            SettingRoute()
                        }
                    }
                    entry<Detail>(
                        metadata = metadata {
                            put(DetailEntryMarker, true)
                        },
                    ) { detail ->
                        with(sharedTransitionScope) {
                            DetailRoute(
                                animatedVisibilityScope = navAnimatedVisibilityScope(),
                                detail = detail,
                            )
                        }
                    }
                },
            ),
            // Detail 이 오버레이로 뜨고, 그 외 entry 는 단일 pane 으로 그린다.
            // nav3 도 fallback 을 주지만, 목록에 두는 편이 의도가 분명하다.
            sceneStrategies = listOf(DetailOverlaySceneStrategy(), SinglePaneSceneStrategy()),
            onBack = { navState.goBack() },
            sharedTransitionScope = this,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

private fun BrbaCharacter.toDetail(): Detail =
    Detail(
        id = charId,
        image = img,
    )