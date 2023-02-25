package com.dart69.coroutines.flows

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

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

    @OptIn(FlowPreview::class)
    abstract class AbstractDebouncedFlow<T>(
        private val timeOut: Long,
    ) : MutableDebounceFlow<T> {
        init {
            if (timeOut < 0L) {
                throw IllegalArgumentException("Timeout can't be < 0, actual is $timeOut")
            }
        }

        protected abstract val trigger: MutableSharedFlow<T>

        override suspend fun collect(collector: FlowCollector<T>) {
            trigger
                .distinctUntilChanged()
                .debounce(timeOut)
                .collect(collector)
        }

        override fun emit(value: T) {
            trigger.tryEmit(value)
        }
    }
}

@Suppress("FunctionName", "NotConstructor")
fun <T> MutableDebouncedFlow(
    initial: T,
    timeOut: Long,
): MutableDebounceFlow<T> = object : MutableDebounceFlow.AbstractDebouncedFlow<T>(timeOut) {
    override val trigger: MutableStateFlow<T> = MutableStateFlow(initial)
}

@Suppress("FunctionName", "NotConstructor")
fun <T> MutableDebouncedFlow(
    timeOut: Long,
): MutableDebounceFlow<T> = object : MutableDebounceFlow.AbstractDebouncedFlow<T>(timeOut) {
    override val trigger: MutableSharedFlow<T> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
}
