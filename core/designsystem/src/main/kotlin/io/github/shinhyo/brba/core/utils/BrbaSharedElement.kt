/*
 * Copyright 2024 shinhyo
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
package io.github.shinhyo.brba.core.utils

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.ui.Modifier

/**
 * 공유 요소 전환 — Navigation 3 기준.
 *
 * `context(scope: SharedTransitionScope)` 로 [SharedTransitionScope] 를 받는다.
 * Kotlin 2.4 의 context parameters 문법(context receivers 후속)이라 named 가 필수다.
 *
 * [animatedVisibilityScope] 는 nav3 의 `LocalNavAnimatedContentScope.current` 로 얻는다 —
 * `AnimatedContentScope` 가 `AnimatedVisibilityScope` 를 확장하므로 그대로 넘길 수 있다.
 */
context(scope: SharedTransitionScope)
fun Modifier.brbaSharedElement(
    isLocalInspectionMode: Boolean,
    animatedVisibilityScope: AnimatedVisibilityScope,
    vararg state: SharedTransitionScope.SharedContentState,
): Modifier {
    if (isLocalInspectionMode) return this
    return with(scope) {
        state.fold(this@brbaSharedElement) { modifier, contentState ->
            modifier.sharedElement(contentState, animatedVisibilityScope)
        }
    }
}