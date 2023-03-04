package com.dart69.coroutines.flows

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

internal data class ForcedValue<T>(
    val data: T,
    val trigger: Any,
)

interface ForcedDebounceFlow<T> : MutableDebounceFlow<T> {

    /**
     * Emits value without equality checking.
     * It means that distinct until changed is ignored
     * */
    fun forceEmit(value: T)

    @OptIn(FlowPreview::class)
    class Default<T>(timeOut: Long) : ForcedDebounceFlow<T> {
        private val trigger = MutableStateFlow<ForcedValue<T>?>(null)
        private val result = trigger
            .debounce(timeOut)
            .filterNotNull()
            .map { it.data }

        override suspend fun collect(collector: FlowCollector<T>) =
            result.collect(collector)

        override fun forceEmit(value: T) {
            trigger.value = ForcedValue(data = value, trigger = Any())
        }

        override fun emit(value: T) {
            trigger.update {
                it?.copy(data = value) ?: ForcedValue(data = value, trigger = Any())
            }
        }
    }
}

@Suppress("FunctionName", "NotConstructor")
fun <T> ForcedDebounceFlow(
    timeOut: Long,
): ForcedDebounceFlow<T> = ForcedDebounceFlow.Default(timeOut = timeOut)