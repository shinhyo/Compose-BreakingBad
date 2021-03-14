package io.github.shinhyo.brba.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList

fun <T> Flow<T>.toListSingle(): Flow<List<T>> = flow {
    emit(toList(mutableListOf()))
}
