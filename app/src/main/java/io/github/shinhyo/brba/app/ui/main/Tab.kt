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
package io.github.shinhyo.brba.app.ui.main

import io.github.shinhyo.brba.R
import io.github.shinhyo.brba.feature.favorate.navigation.FAVORITE_ROUTE
import io.github.shinhyo.brba.feature.main.navigation.LIST_ROUTE

enum class Tab(
    val label: String,
    val icon: Int,
    val route: String
) {
    LIST("Character", R.drawable.ic_account_cowboy_hat, LIST_ROUTE),
    FAVORITE("Favorite", R.drawable.ic_heart, FAVORITE_ROUTE)
}