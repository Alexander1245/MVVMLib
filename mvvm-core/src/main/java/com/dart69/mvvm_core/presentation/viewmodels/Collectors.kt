package com.dart69.mvvm_core.presentation.viewmodels

import kotlinx.coroutines.flow.FlowCollector

interface UiState

interface UiStateCollector<T : UiState> {
    suspend fun collectUiStates(collector: FlowCollector<T>)
}

interface UiEvent

interface UiEventCollector<T : UiEvent> {
    suspend fun collectUiEvents(collector: FlowCollector<T>)
}