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
package io.github.shinhyo.brba.feature.bottombar.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * 하단 탭 화면 루트 — nav3 키.
 *
 * Nav2 는 이 위에 또 하나의 `NavHost` 를 겹쳐서 탭을 바꿨다. nav3 는 탭마다 back stack 을
 * 따로 둬서 [BrbaNavigationState] 가 대신 그 역할을 한다.
 */
@Serializable
data object BottomBar : NavKey