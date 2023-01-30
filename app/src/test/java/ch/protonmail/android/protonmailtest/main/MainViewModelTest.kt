package ch.protonmail.android.protonmailtest.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ch.protonmail.android.protonmailtest.MainDispatcherRule
import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import ch.protonmail.android.protonmailtest.domain.usecase.GetTasksUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val getTasksUseCase: GetTasksUseCase = mockk(relaxed = true)

    private val viewModel: MainViewModel by lazy { MainViewModel(getTasksUseCase) }

    private val task = TaskDomain(Date(), Date(), "description", "title", "id", "image", false)
    private val taskList = listOf(task)

    @Test
    fun `Given new tasks, When are saved into the db, Then set the viewState to Success`() = runTest {
        coEvery { getTasksUseCase.getTasks() } returns flowOf(taskList)

        viewModel.fetchTasks()

        coVerify { getTasksUseCase.getTasks() }

        assertTrue(viewModel.viewState.value is MainViewModel.ViewState.Success)
        assertEquals(taskList, (viewModel.viewState.value as MainViewModel.ViewState.Success).data)
    }
}