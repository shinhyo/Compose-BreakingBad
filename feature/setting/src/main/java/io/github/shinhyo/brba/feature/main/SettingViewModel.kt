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
package io.github.shinhyo.brba.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.shinhyo.brba.core.domain.repository.DeviceRepository
import io.github.shinhyo.brba.core.model.BrbaDeviceData
import io.github.shinhyo.brba.core.model.BrbaThemeMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingData(
    val deviceData: BrbaDeviceData,
)

sealed interface SettingUiState {
    data class Success(val settingData: SettingData) : SettingUiState
    data object Loading : SettingUiState
}

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository,
) : ViewModel() {

    val uiState: StateFlow<SettingUiState> = deviceRepository.deviceData
        .map { themeMode ->
            SettingUiState.Success(SettingData(deviceData = themeMode))
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = SettingUiState.Loading,
            started = SharingStarted.WhileSubscribed(5000),
        )

    fun onChangeThemeClick(themeMode: BrbaThemeMode) {
        viewModelScope.launch {
            deviceRepository.setThemeMode(themeMode)
        }
    }
}