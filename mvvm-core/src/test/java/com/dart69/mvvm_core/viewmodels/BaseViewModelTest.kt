package com.dart69.mvvm_core.viewmodels

import com.dart69.mvvm_core.presentation.viewmodels.BaseViewModel
import com.dart69.mvvm_core.presentation.viewmodels.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test

internal data class FakeUiState(
    val title: String,
    val content: String,
) : UiState {
    companion object {
        val INITIAL = FakeUiState(
            title = "",
            content = "",
        )
    }
}

internal class FakeBaseViewModel : BaseViewModel.Default<FakeUiState>(FakeUiState.INITIAL) {
    fun updateTitle(newTitle: String) {
        uiStates.update { it.copy(title = newTitle) }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
internal class BaseViewModelTest {
    private lateinit var viewModel: FakeBaseViewModel

    @Before
    fun beforeEach() {
        viewModel = FakeBaseViewModel()
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
        assertArrayEquals(expected.toTypedArray(), actual.toTypedArray())
    }
}