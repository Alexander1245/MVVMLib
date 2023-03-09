package com.dart69.mvvm_core.presentation.viewmodels

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow

interface UiEvent

interface UiEventObserver<T : UiEvent> {
    fun observeUiEvents(): Flow<T>
}

interface MutableUiEventObserver<T : UiEvent> : UiEventObserver<T> {
    suspend fun sendUiEvent(event: T)

    class ChannelObserver<T : UiEvent> : MutableUiEventObserver<T> {
        private val uiEvents = Channel<T>(Channel.BUFFERED)

        override fun observeUiEvents(): Flow<T> = uiEvents.receiveAsFlow()

        override suspend fun sendUiEvent(event: T) = uiEvents.send(event)
    }

    class SharedObserver<T : UiEvent> : MutableUiEventObserver<T> {
        private val uiEvents = MutableSharedFlow<T>()

        override fun observeUiEvents(): Flow<T> = uiEvents.asSharedFlow()

        override suspend fun sendUiEvent(event: T) = uiEvents.emit(event)
    }
}