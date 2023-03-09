package com.dart69.mvvm_core.presentation.viewmodels

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal data class FakeUiState(
    val title: String,
    val content: String,
): UiState {
    companion object {
        val INITIAL = FakeUiState("", "")
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
internal class MutableUiStateObserverTest {
    private lateinit var observer: MutableUiStateObserver.Default<FakeUiState>

    @Before
    fun beforeEach() {
        observer = MutableUiStateObserver.Default(FakeUiState.INITIAL)
    }

    @Test
    fun `the observer is hot`() = runBlocking {
        val expected = listOf(FakeUiState.INITIAL, FakeUiState("a", "a"))
        val uiStates = List(2) {
            async(UnconfinedTestDispatcher()) {
                observer.observeUiStates().take(expected.size).toList()
            }
        }
        observer.sendUiState(expected.last())
        uiStates.forEach { actual ->
            assertEquals(expected, actual.await())
        }
    }

    @Test
    fun `the observer is distinct until changed`() = runBlocking {
        var actual = 0
        val expected = 1
        val job = launch {
            withTimeout(1500L) {
                observer.observeUiStates().collect {
                    ++actual
                }
            }
        }
        repeat(5) { observer.sendUiState(FakeUiState.INITIAL) }
        job.join()
        assertEquals(expected, actual)
    }

    @Test
    fun `updateUiState updates only a title of the FakeUiState`() = runBlocking {
        val newTitle = "newTitle"
        val expected = listOf(FakeUiState.INITIAL, FakeUiState.INITIAL.copy(title = newTitle))
        val actual = async(UnconfinedTestDispatcher()) {
            observer.observeUiStates().take(expected.size).toList()
        }
        observer.updateUiState {
            it.copy(title = newTitle)
        }
        assertEquals(expected, actual.await())
    }
}