package com.dart69.mvvm_core.coroutines

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

interface LifecycleCollector {
    fun <T> Flow<T>.collectWithLifecycle(
        lifecycleOwner: LifecycleOwner,
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        collector: FlowCollector<T>
    )

    class Default : LifecycleCollector {
        override fun <T> Flow<T>.collectWithLifecycle(
            lifecycleOwner: LifecycleOwner,
            lifecycleState: Lifecycle.State,
            collector: FlowCollector<T>
        ) {
            val flow = this
            lifecycleOwner.lifecycleScope.launch {
                lifecycleOwner.apply {
                    repeatOnLifecycle(lifecycleState) {
                        flow.collect(collector)
                    }
                }
            }
        }
    }
}