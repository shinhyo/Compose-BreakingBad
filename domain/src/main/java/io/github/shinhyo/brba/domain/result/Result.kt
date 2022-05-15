package io.github.shinhyo.brba.domain.result

import io.github.shinhyo.brba.domain.result.Result.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Success && data != null

fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Success<T>)?.data ?: fallback
}

inline fun <R, T> Result<T>.mapTransform(transform: (T) -> R): Result<R> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Error -> Result.Error(exception)
}

inline fun <R, T> Flow<Result<T>>.mapTransform(crossinline transform: (T) -> R): Flow<Result<R>> =
    this.map {
        when (it) {
            is Result.Success -> Result.Success(transform(it.data))
            is Result.Error -> Result.Error(it.exception)
        }
    }