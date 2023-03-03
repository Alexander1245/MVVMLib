package com.dart69.coroutines.results

interface BaseResults<T>

sealed class Results<T> : BaseResults<T> {
    data class Loading<T>(val isInProgress: Boolean = true) : Results<T>()

    data class Completed<T>(val data: T) : Results<T>()

    data class Error<T>(val throwable: Throwable) : Results<T>()
}
fun <T> Results<T>.takeCompleted(): T? = if (this is Results.Completed) data else null