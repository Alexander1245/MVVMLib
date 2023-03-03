package com.dart69.mvvm_core.viewmodels

import androidx.lifecycle.viewModelScope
import com.dart69.mvvm_core.presentation.viewmodels.CommunicatorViewModel
import com.dart69.mvvm_core.presentation.viewmodels.UiEvent
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal data class FakeUiEvent(
    val message: String
) : UiEvent

internal class FakeCommunicatorViewModel :
    CommunicatorViewModel.Default<FakeUiState, FakeUiEvent>(FakeUiState.INITIAL) {
    fun updateTitle(newTitle: String) {
        uiStates.update { it.copy(title = newTitle) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun sendMessage(message: String) {
        viewModelScope.launch(UnconfinedTestDispatcher()) {
            uiEvents.send(FakeUiEvent(message))
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
internal class CommunicatorViewModelTest {
    private lateinit var viewModel: FakeCommunicatorViewModel

    @Before
    fun beforeEach() {
        viewModel = FakeCommunicatorViewModel()
    }

    @Test
    fun `test message is delivered`() = runBlocking {
        val message = "message"
        val expected = FakeUiEvent(message)
        val actual = mutableListOf<FakeUiEvent>()
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.observeUiEvents().take(1).collect {
                actual += it
            }
        }
        viewModel.sendMessage(message)
        job.join()
        assertEquals(expected, actual.first())
    }

    @Test
    fun `the viewModel's updateTitle notifies the ui state collector`() = runBlocking {
        val newTitle = "new title"
        val expected = listOf(FakeUiState.INITIAL, FakeUiState.INITIAL.copy(title = newTitle))
        val actual = mutableListOf<FakeUiState>()
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.observeUiStates().take(expected.size).collect {
                actual += it
            }
        }
        viewModel.updateTitle(newTitle)
        job.join()
        Assert.assertArrayEquals(expected.toTypedArray(), actual.toTypedArray())
    }
}