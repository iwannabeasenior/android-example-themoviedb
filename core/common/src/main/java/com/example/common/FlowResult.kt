package com.example.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface FlowResult<out T> {
    data class Success<T>(val data: T): FlowResult<T>
    data class Error(val exception: Throwable): FlowResult<Nothing>
    data object Loading: FlowResult<Nothing>
}

// convert Flow<T> -> Flow<Result>
fun <T> Flow<T>.asResult(): Flow<FlowResult<T>> = map<T, FlowResult<T>> { FlowResult.Success(it) }
    .onStart { emit(FlowResult.Loading) }
    .catch { emit(FlowResult.Error(it))  }