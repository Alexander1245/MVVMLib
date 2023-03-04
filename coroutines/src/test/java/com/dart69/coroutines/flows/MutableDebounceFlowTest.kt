package com.dart69.coroutines.flows

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

internal open class MutableDebounceFlowTest {

    protected open fun createFlow(timeOut: Long): MutableDebounceFlow<Int> =
        MutableDebounceFlow(timeOut = timeOut)

    @Test
    fun `an attempt to create a flow with the negative timOut throws IllegalArgumentException`() {
        assertThrows<IllegalArgumentException> {
            createFlow(timeOut = -1L)
        }
    }

    @Test
    fun `the flow is hot`() = runBlocking {
        val flow = createFlow(timeOut = 0L)

        val count = 10
        val values = List(count) { it }

        val expected1 = values.take(count / 2)
        val expected2 = values.takeLast(count / 2)

        val actual1 = async { flow.take(expected1.size).toList() }
        expected1.forEach {
            flow.emit(it)
            delay(10L)
        }
        assertContentEquals(expected1, actual1.await())

        val actual2 = async { flow.take(expected2.size).toList() }
        expected2.forEach {
            flow.emit(it)
            delay(10L)
        }
        assertContentEquals(expected2, actual2.await())
    }

    @Test
    fun `the flow is distinct until changed`() = runBlocking {
        var collectorCalls = 0
        val flow = createFlow(timeOut = 1L)
        val job = launch {
            withTimeout(1500L) {
                flow.collect { ++collectorCalls }
            }
        }
        repeat(10) {
            flow.emit(0)
            delay(10L)
        }
        job.join()
        assertEquals(1, collectorCalls)
    }

    @Test
    fun `the flow filters values until a timeout not exceeded`() = runBlocking {
        val flow = createFlow(timeOut = 15L)
        val actual = async {
            flow.first()
        }
        repeat(10, flow::emit)
        assertEquals(9, actual.await())
    }
}