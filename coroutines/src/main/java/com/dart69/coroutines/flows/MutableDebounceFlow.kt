package com.dart69.coroutines.flows

import kotlinx.coroutines.flow.Flow

/**
 * A regular hot flow with timeout filtering applied on it.
 * Note that all repeated values will be filtered out.
 * @see kotlinx.coroutines.flow.distinctUntilChanged
 * */
interface DebounceFlow<T> : Flow<T>

/**
 * @see com.dart69.coroutines.flows.DebounceFlow
 * */
interface MutableDebounceFlow<T> : DebounceFlow<T> {

    /**
     * Emits value to the flow only if the timeout was out.
     * In another case a previous emitted value will be dropped.
     * @see kotlinx.coroutines.flow.debounce
     * */
    fun emit(value: T)
}

@Suppress("FunctionName", "NotConstructor")
fun <T> MutableDebounceFlow(
    timeOut: Long,
): MutableDebounceFlow<T> = ForcedDebounceFlow.Default(timeOut = timeOut)
