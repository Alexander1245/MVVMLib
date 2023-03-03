package com.dart69.mvvm_core.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

abstract class BaseViewModel<T : UiState> : ViewModel(), UiStateObserver<T> {
    protected abstract val uiStates: MutableStateFlow<T>

    override suspend fun observeUiStates(): Flow<T> = uiStates.asStateFlow()

    abstract class Default<T : UiState>(initialState: T) : BaseViewModel<T>() {
        override val uiStates: MutableStateFlow<T> = MutableStateFlow(initialState)
    }
}

abstract class CommunicatorViewModel<T : UiState, E : UiEvent> : BaseViewModel<T>(),
    UiEventObserver<E> {
    protected abstract val uiEvents: Channel<E>

    override suspend fun observeUiEvents(): Flow<E> =
        uiEvents.consumeAsFlow()


    abstract class Default<T : UiState, E : UiEvent>(
        initialState: T
    ) : CommunicatorViewModel<T, E>() {
        override val uiEvents: Channel<E> = Channel()
        override val uiStates: MutableStateFlow<T> = MutableStateFlow(initialState)
    }
}