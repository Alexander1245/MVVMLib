package com.dart69.mvvm_core.presentation.viewmodels

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface UiState

interface UiStateObserver<T : UiState> {
    fun observeUiStates(): Flow<T>
}

interface MutableUiStateObserver<T : UiState> : UiStateObserver<T> {
    fun sendUiState(state: T)

    fun updateUiState(updater: (T) -> T)

    class Default<T : UiState>(initialState: T) : MutableUiStateObserver<T> {
        private val uiStates = MutableStateFlow(initialState)

        override fun observeUiStates(): Flow<T> = uiStates.asStateFlow()

        override fun sendUiState(state: T) {
            uiStates.value = state
        }

        override fun updateUiState(updater: (T) -> T) {
            uiStates.update(updater)
        }
    }
}