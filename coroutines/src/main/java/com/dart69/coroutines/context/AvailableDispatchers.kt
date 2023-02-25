package com.dart69.coroutines.context

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * The interface to omit hardcoded coroutine dispatchers and improve testability
 * */
interface AvailableDispatchers {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
    val unconfined: CoroutineDispatcher

    class AppDispatchers : AvailableDispatchers {
        override val io: CoroutineDispatcher get() = Dispatchers.IO
        override val default: CoroutineDispatcher get() = Dispatchers.Default
        override val main: CoroutineDispatcher get() = Dispatchers.Main
        override val unconfined: CoroutineDispatcher get() = Dispatchers.Unconfined
    }
}