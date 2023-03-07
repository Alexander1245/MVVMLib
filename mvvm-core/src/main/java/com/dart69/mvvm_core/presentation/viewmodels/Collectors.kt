package com.dart69.mvvm_core.presentation.viewmodels

import kotlinx.coroutines.flow.Flow

interface UiState

interface UiStateObserver<T : UiState> {
    fun observeUiStates(): Flow<T>
}

interface UiEvent

interface UiEventObserver<T : UiEvent> {
    fun observeUiEvents(): Flow<T>
}