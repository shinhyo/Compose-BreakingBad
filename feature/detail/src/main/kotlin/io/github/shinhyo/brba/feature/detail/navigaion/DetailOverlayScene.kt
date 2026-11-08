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
package io.github.shinhyo.brba.feature.detail.navigaion

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.NavMetadataKey
import androidx.navigation3.runtime.get
import androidx.navigation3.scene.OverlayScene
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope

/**
 * 디테일 destination — 탭별 `NavBackStack` 에 push 되고 [DetailOverlaySceneStrategy] 가
 * [OverlayScene] 으로 띄운다.
 *
 * Nav2 는 디테일을 별도 `NavHost` 로 빼서 탭바를 없앴다. nav3 오버레이로 바꾸면
 * 아래 탭 화면이 합성 상태로 남아 리스트 스크롤이 유지된다.
 */
object DetailEntryMarker : NavMetadataKey<Boolean>

/**
 * equals/hashCode 는 [detail] 만 본다 — 엔트리 인스턴스가 recomposition 마다 바뀌어도 같은 scene 으로 봐야
 * 오버레이 수명·애니메이션이 꼬이지 않는다.
 *
 * nav3 문서: "Implementations of this interface should either be data classes, or implement
 * equals and hashcode to ensure that the same Scene is used when appropriate."
 */
class DetailOverlayScene(
    val detail: Detail,
    override val entries: List<NavEntry<NavKey>>,
    override val previousEntries: List<NavEntry<NavKey>>,
) : OverlayScene<NavKey> {
    override val key: Any = detail
    override val overlaidEntries: List<NavEntry<NavKey>> = previousEntries

    // 디테일 본문은 entry 의 내용으로 그린다 — 그래야 디테일마다 ViewModel 이 따로 생기고
    // 백스택에서 빠질 때 정리된다.
    override val content: @Composable () -> Unit = {
        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
        ) {
            entries.last().Content()
        }
    }

    override fun equals(other: Any?): Boolean =
        other is DetailOverlayScene && other.detail == detail

    override fun hashCode(): Int = detail.hashCode()
}

/** 백스택 꼭대기가 디테일 entry 일 때만 개입한다. */
class DetailOverlaySceneStrategy : SceneStrategy<NavKey> {
    override fun SceneStrategyScope<NavKey>.calculateScene(
        entries: List<NavEntry<NavKey>>,
    ): Scene<NavKey>? {
        val last = entries.lastOrNull() ?: return null
        if (last.metadata[DetailEntryMarker] != true) return null
        val detail = last.contentKey as? Detail ?: return null
        return DetailOverlayScene(
            detail = detail,
            entries = listOf(last),
            previousEntries = entries.dropLast(1),
        )
    }
}

/**
 * 공유 요소 전환에 필요한 [AnimatedVisibilityScope].
 *
 * nav3 의 `LocalNavAnimatedContentScope` 가 `AnimatedContentScope` 를 준다.
 * `AnimatedContentScope` 는 `AnimatedVisibilityScope` 를 확장하므로 그대로 넘길 수 있다.
 *
 * 오버레이 scene 도 같은 scope 를 제공하지만, 그건 다른 entry 의 AnimatedContentScope 다.
 * 그래도 공유 요소 전환이 끊기지 않고 동작한다.
 */
@Composable
fun navAnimatedVisibilityScope(): AnimatedVisibilityScope =
    androidx.navigation3.ui.LocalNavAnimatedContentScope.current