package com.dart69.coroutines.flows

import com.dart69.coroutines.results.Results
import kotlinx.coroutines.flow.*

typealias ResultsFlow<T> = Flow<Results<T>>
typealias ResultsSharedFlow<T> = SharedFlow<Results<T>>
typealias MutableResultsSharedFlow<T> = MutableSharedFlow<Results<T>>
typealias ResultsStateFlow<T> = StateFlow<Results<T>>
typealias MutableResultsStateFlow<T> = MutableStateFlow<Results<T>>

fun <T> resultsFlowOf(block: suspend () -> T): ResultsFlow<T> = flow<Results<T>> {
    emit(Results.Completed(block()))
}.onStart {
    emit(Results.Loading(true))
}.catch { throwable ->
    emit(Results.Error(throwable))
}

@Suppress("FunctionName")
fun <T> MutableResultsStateFlow(initial: Results<T>): MutableResultsStateFlow<T> =
    MutableStateFlow(initial)

@Suppress("FunctionName")
fun <T> MutableResultsSharedFlow(): MutableResultsSharedFlow<T> =
    MutableSharedFlow()

suspend fun <T> MutableResultsSharedFlow<T>.emitResults(block: suspend () -> T) {
    emitAll(resultsFlowOf(block))
}