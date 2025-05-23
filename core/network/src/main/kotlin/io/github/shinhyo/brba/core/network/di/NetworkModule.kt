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
package io.github.shinhyo.brba.core.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.shinhyo.brba.core.network.LocalDataSource
import io.github.shinhyo.brba.core.network.NetworkDataSource

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    /**
     * NOTE: This is a sample app.
     *
     * The actual network implementation can be replaced with a local data source
     * for testing or demo purposes without real network calls.
     */
//    @Binds
//    fun bindNetworkDataSource(
//        network: RetrofitNetwork,
//    ): NetworkDataSource

    @Binds
    fun bindNetworkDataSource(
        network: LocalDataSource,
    ): NetworkDataSource
}