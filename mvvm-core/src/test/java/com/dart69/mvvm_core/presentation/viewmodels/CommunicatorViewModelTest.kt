package com.dart69.mvvm_core.presentation.viewmodels

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class FakeViewModel(
    private val coroutineDispatcher: CoroutineDispatcher,
) : CommunicatorViewModel<FakeUiState, FakeUiEvent>(
    uiStates = MutableUiStateObserver.Default(FakeUiState.INITIAL),
    uiEvents = MutableUiEventObserver.ChannelObserver(),
) {
    fun changeTitle(newTitle: String) {
        uiStates.updateUiState {
            it.copy(title = newTitle)
        }
    }

    fun showMessage(message: String) {
        uiEvents.launchUiEvent(FakeUiEvent(message), coroutineDispatcher)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
internal class CommunicatorViewModelTest {
    private lateinit var viewModel: FakeViewModel

    @Before
    fun beforeEach() {
        viewModel = FakeViewModel(UnconfinedTestDispatcher())
    }

    @Test
    fun `the message is shown`() = runBlocking {
        val message = "msg"
        val expected = FakeUiEvent(message)
        val actual = async { viewModel.observeUiEvents().first() }
        viewModel.showMessage(message)
        assertEquals(expected, actual.await())
    }

    @Test
    fun `the title is updated`() = runBlocking {
        val title = "newTitle"
        val expected = FakeUiState.INITIAL.copy(title = title)
        val actual = async(UnconfinedTestDispatcher()) {
            viewModel.observeUiStates().drop(1).first()
        }
        viewModel.changeTitle(title)
        assertEquals(expected, actual.await())
    }
}