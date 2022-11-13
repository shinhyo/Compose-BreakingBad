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
package io.github.shinhyo.brba.core.common.result

import io.github.shinhyo.brba.core.common.result.Result.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {
    object Loading : Result<Nothing>
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable? = null) : Result<Nothing>
}

//
// /**
// * `true` if [Result] is of type [Success] & holds non-null [Success.data].
// */
val Result<*>.succeeded
    get() = this is Success && data != null

fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Success<T>)?.data ?: fallback
}
//
// inline fun <R, T> Result<T>.mapTransform(transform: (T) -> R): Result<R> = when (this) {
//    is Success -> Success(transform(data))
//    is Error -> Error(exception)
//    else -> Error(IllegalStateException())
// }
//
// inline fun <R, T> Flow<Result<T>>.mapTransform(crossinline transform: (T) -> R): Flow<Result<R>> =
//    this.map {
//        when (it) {
//            is Success -> Success(transform(it.data))
//            is Error -> Error(it.exception)
//            else -> Error(IllegalStateException())
//        }
//    }

fun <T> Flow<T>.asResult(): Flow<Result<T>> = this
    .map<T, Result<T>> { Success(it) }
    .onStart { emit(Result.Loading) }
    .catch { emit(Result.Error(it)) }
