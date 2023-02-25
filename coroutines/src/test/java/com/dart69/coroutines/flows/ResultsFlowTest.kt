package com.dart69.coroutines.flows

import com.dart69.coroutines.results.Results
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

internal class ResultsFlowTest {

    @Test
    fun `the flow emits a loading result and a completed result`() = runBlocking {
        val data = 11
        val expected = listOf(
            Results.Loading(),
            Results.Completed(data)
        )
        val actual = resultsFlowOf { data }.toList()
        assertContentEquals(expected, actual)
    }

    @Test
    fun `the flow wraps errors to the error result`() = runBlocking {
        val throwable = IllegalArgumentException()
        val expected = listOf(
            Results.Loading<Int>(),
            Results.Error(throwable)
        )
        val actual = resultsFlowOf<Int> { throw throwable }.toList()
        assertContentEquals(expected, actual)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `emitAll emits all of the resultsFlow results`() = runBlocking {
        val data = 11
        val sharedFlow = MutableResultsSharedFlow<Int>()
        val expected = listOf(
            Results.Loading(),
            Results.Completed(data)
        )
        val actual = async(UnconfinedTestDispatcher()) {
            sharedFlow.take(expected.size).toList()
        }
        sharedFlow.emitResults { data }
        assertContentEquals(expected, actual.await())
    }
}