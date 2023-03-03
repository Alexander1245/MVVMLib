package com.dart69.mvvm_core.presentation.viewmodels

import kotlinx.coroutines.flow.Flow

interface UiState

interface UiStateObserver<T : UiState> {
    suspend fun observeUiStates(): Flow<T>
}

interface UiEvent

interface UiEventObserver<T : UiEvent> {
    suspend fun observeUiEvents(): Flow<T>
}