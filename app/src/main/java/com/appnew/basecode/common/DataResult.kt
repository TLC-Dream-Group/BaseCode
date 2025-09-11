@file:Suppress("UNUSED")

package com.appnew.basecode.common

sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(
        val error: Throwable? = null,
        val cause: ExceptionCause? = null,
    ) : DataResult<Nothing>()
}

inline fun <T,R> DataResult<T>.fold(
    onSuccess: (T) -> R,
    onError: (Throwable?, ExceptionCause?) -> R
) = when (this) {
    is DataResult.Success -> onSuccess(data)
    is DataResult.Error -> onError(error, cause)
}

fun <T> DataResult<T>.isError(): Boolean = this is DataResult.Error

@Suppress("UNCHECKED_CAST")
fun <T, R> DataResult<T>.cast(): DataResult<R> = this as DataResult<R>

val <T>DataResult<T>.data: T?
    get() = when (this) {
        is DataResult.Success -> this.data
        is DataResult.Error -> null
    }

inline fun <T> DataResult<T>.onError(action: (DataResult.Error) -> Unit): DataResult<T> {
    if (this is DataResult.Error) {
        action(this)
    }
    return this
}
