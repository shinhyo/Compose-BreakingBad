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
package io.github.shinhyo.brba.core.data.repository

import io.github.shinhyo.brba.core.datastore.DeviceDataSource
import io.github.shinhyo.brba.core.domain.repository.DeviceRepository
import io.github.shinhyo.brba.core.model.BrbaDeviceData
import io.github.shinhyo.brba.core.model.BrbaThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val deviceDataSource: DeviceDataSource,
) : DeviceRepository {

    override val deviceData: Flow<BrbaDeviceData> = deviceDataSource.deviceDataFlow
        .map {
            BrbaDeviceData(
                themeMode = it.themeMode,
            )
        }

    override suspend fun setThemeMode(themeMode: BrbaThemeMode) {
        deviceDataSource.setThemeMode(themeMode)
    }
}