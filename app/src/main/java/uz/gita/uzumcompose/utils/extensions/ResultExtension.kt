package uz.gita.uzumcompose.utils.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

fun <T> Flow<Result<T>>.onSuccess(action: suspend (T) -> Unit): Flow<Result<T>> =
    onEach { result ->
        result.onSuccess { action(it) }
    }
fun <T> Flow<Result<T>>.onFailure(action: suspend (Throwable) -> Unit): Flow<Result<T>> =
    onEach { result ->
        result.onFailure { action(it) }
    }