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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import io.github.shinhyo.brba.core.ui.BrBaNavigationBar
import io.github.shinhyo.brba.core.ui.BrbaNavigationBarItem

/** 탭바가 차지하는 높이(px) — 본문이 하단 여백으로 삼는다. */
val LocalTabBarHeightPx = compositionLocalOf { 0 }

/**
 * 탭바 — 본문 위에 겹쳐 그린다(오버레이).
 *
 * Nav2 는 `Scaffold(bottomBar=...)` 로 본문을 밀어 올렸다. nav3 로 오면서 탭바를
 * 본문 위에 겹치게 하면 탭 전환 때 레이아웃이 흔들리지 않고, 디테일 오버레이가
 * 탭바 아래로 자연스럽게 깔린다.
 *
 * 탭바 높이는 본문이 하단 inset 으로 알아야 하므로 [LocalTabBarHeightPx] 로 전달한다.
 */
@Composable
fun BrbaTabBarOverlay(
    currentTab: Tab,
    onSelectTab: (Tab) -> Unit,
    modifier: Modifier = Modifier,
) {
    var tabBarHeightPx by remember { mutableIntStateOf(0) }

    CompositionLocalProvider(LocalTabBarHeightPx provides tabBarHeightPx) {
        Box(modifier = modifier.fillMaxSize()) {
            BrBaNavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .onSizeChanged { tabBarHeightPx = it.height },
            ) {
                for (tab in remember { Tab.entries }) {
                    BrbaNavigationBarItem(
                        onSelected = { currentTab == tab },
                        onClick = { onSelectTab(tab) },
                        icon = {
                            Icon(
                                painterResource(id = tab.icon),
                                contentDescription = tab.label,
                            )
                        },
                    )
                }
            }
        }
    }
}

/**
 * 본문 + 탭바 오버레이.
 *
 * @param applyTopInset 본문이 상태바 뒤까지 뻗는지 — 목록은 아래로, 오버레이만 피한다.
 */
@Composable
fun BrbaTabScaffold(
    currentTab: Tab,
    onSelectTab: (Tab) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier =
        modifier
            .fillMaxSize(),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }
        BrbaTabBarOverlay(
            currentTab = currentTab,
            onSelectTab = onSelectTab,
        )
    }
}