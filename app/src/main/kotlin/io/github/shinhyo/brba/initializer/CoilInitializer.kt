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
package io.github.shinhyo.brba.initializer

import android.content.Context
import androidx.startup.Initializer
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import okio.Path.Companion.toPath

class CoilInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        // coil3 는 `Coil.setImageLoader` 대신 `SingletonImageLoader.setUnsafe` 를 쓴다.
        SingletonImageLoader.setUnsafe(
            ImageLoader.Builder(context)
                .memoryCache {
                    // coil3 의 `MemoryCache.Builder` 는 인자 없는 생성자다.
                    MemoryCache.Builder()
                        .maxSizePercent(context, 0.25)
                        .build()
                }
                // coil3 에서 `respectCacheHeaders(false)` 는 사라지고 캐시 정책으로 대신한다.
                .networkCachePolicy(CachePolicy.ENABLED)
                .diskCache {
                    DiskCache.Builder()
                        // coil3 는 okio Path 를 받는다 — `File` 을 그대로 넘기면 안 된다.
                        .directory(context.cacheDir.resolve("image_cache").path.toPath())
                        .maxSizePercent(0.10)
                        .build()
                }
                .build(),
        )
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}