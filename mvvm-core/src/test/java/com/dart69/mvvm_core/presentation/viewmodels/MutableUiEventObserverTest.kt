package com.dart69.mvvm_core.presentation.viewmodels

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal data class FakeUiEvent(val message: String) : UiEvent

@OptIn(ExperimentalCoroutinesApi::class)
internal class MutableUiEventObserverTest {
    private lateinit var observer: MutableUiEventObserver.ChannelObserver<FakeUiEvent>

    @Before
    fun beforeEach() {
        observer = MutableUiEventObserver.ChannelObserver()
    }

    @Test
    fun `the observer is not distinct until changed`() = runBlocking {
        val count = 3
        val expected = List(count) { FakeUiEvent("msg") }
        val actual = async(UnconfinedTestDispatcher()) {
            observer.observeUiEvents().take(count).toList()
        }
        expected.forEach { event ->
            observer.sendUiEvent(event)
        }
        assertEquals(expected, actual.await())
    }
}