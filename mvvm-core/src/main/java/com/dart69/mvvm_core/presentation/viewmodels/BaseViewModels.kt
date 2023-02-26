package com.dart69.mvvm_core.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow

abstract class BaseViewModel<T : UiState>(
    initialState: T
) : ViewModel(), UiStateCollector<T> {
    protected val uiStates: MutableStateFlow<T> = MutableStateFlow(initialState)

    override suspend fun collectUiStates(collector: FlowCollector<T>) {
        uiStates.collect(collector)
    }
}

abstract class CommunicatorViewModel<T : UiState, E : UiEvent>(
    initialState: T
) : BaseViewModel<T>(initialState), UiEventCollector<E> {
    protected val uiEvents: Channel<E> = Channel()

    override suspend fun collectUiEvents(collector: FlowCollector<E>) {
        uiEvents.consumeAsFlow().collect(collector)
    }
}