/*
 * Copyright 2023 shinhyo
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

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * 하단 탭 — Navigation 3 키이자 탭 표시 정보의 단일 원천.
 *
 * nav3 는 back stack 을 이름으로 직렬화하므로 [Serializable] 이 필요하다.
 * 문자열 라우트 대신 enum 자체를 키로 쓴다 — 문자열 라우트는 오타가 런타임에만 드러난다.
 */
@Serializable
enum class Tab(
    val label: String,
    val icon: Int,
) : NavKey {
    LIST(label = "Character", icon = R.drawable.ic_account_cowboy_hat),
    FAVORITE(label = "Favorite", icon = R.drawable.ic_heart),
    SETTING(label = "Setting", icon = R.drawable.ic_flask_empty),
}