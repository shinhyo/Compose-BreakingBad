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
package io.github.shinhyo.brba.core.network.retrofit

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.shinhyo.brba.core.network.BuildConfig
import io.github.shinhyo.brba.core.network.NetworkDataSource
import io.github.shinhyo.brba.core.network.model.CharacterResponse
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

private interface BaBrApi {

    companion object {
        const val BASE_URL = "https://brba.shinhyo.workers.dev"
    }

    @GET(value = "/api/characters")
    suspend fun getCharacters(): List<CharacterResponse>

    @GET(value = "/api/characters/{id}")
    suspend fun getCharactersById(
        @Path("id") id: Long,
    ): List<CharacterResponse>
}

@Singleton
class RetrofitNetwork @Inject constructor(
    @ApplicationContext context: Context,
) : NetworkDataSource {

    private val baBrApi by lazy {
        Retrofit.Builder()
            .baseUrl(BaBrApi.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .apply {
                        if (!BuildConfig.DEBUG) return@apply
//                        addInterceptor(
//                            HttpLoggingInterceptor { Timber.tag("OkHttp##\t").d(it) }
//                                .apply { level = HttpLoggingInterceptor.Level.BODY }
//                        )
                    }
                    .cache(Cache(context.cacheDir, 5L * 1024 * 1024))
                    .addInterceptor { forceCache(it) }
                    .build(),
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BaBrApi::class.java)
    }

    private fun forceCache(it: Interceptor.Chain, day: Int = 7): Response {
        val request = it.request().newBuilder().header(
            "Cache-Control",
            "max-stale=" + 60 * 60 * 24 * day,
        ).build()
        val response = it.proceed(request)
        Timber.d("provideOkHttpClient: response: $response")
        Timber.i("provideOkHttpClient: cacheControl: ${response.cacheControl}")
        Timber.i("provideOkHttpClient: networkResponse: ${response.networkResponse}")
        return response
    }

    override suspend fun getCharacter(): List<CharacterResponse> =
        baBrApi.getCharacters()

    override suspend fun getCharacter(id: Long): List<CharacterResponse> =
        baBrApi.getCharactersById(id)
}