package com.dart69.mvvm_core.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<T : UiState>(
    protected val uiStates: MutableUiStateObserver<T>
) : ViewModel(), UiStateObserver<T> by uiStates

abstract class CommunicatorViewModel<T : UiState, E : UiEvent>(
    uiStates: MutableUiStateObserver<T>,
    protected val uiEvents: MutableUiEventObserver<E>,
) : BaseViewModel<T>(uiStates), UiEventObserver<E> by uiEvents {

    protected open fun MutableUiEventObserver<E>.launchUiEvent(
        event: E,
        coroutineContext: CoroutineContext,
    ) {
        viewModelScope.launch(coroutineContext) {
            uiEvents.sendUiEvent(event)
        }
    }
}