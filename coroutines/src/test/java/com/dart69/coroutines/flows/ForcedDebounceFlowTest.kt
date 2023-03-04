package com.dart69.coroutines.flows

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

internal class ForcedDebounceFlowTest : MutableDebounceFlowTest() {
    override fun createFlow(timeOut: Long): ForcedDebounceFlow<Int> =
        ForcedDebounceFlow(timeOut)

    @Test
    fun `forceEmit allows emitting duplicates`() = runBlocking {
        val value = 3
        val expected = List(value) { value }
        val flow = createFlow(20L)
        val actual = async { flow.take(expected.size).toList() }
        expected.forEach {
            flow.forceEmit(it)
            delay(35L)
        }
        assertContentEquals(expected, actual.await())
    }
}