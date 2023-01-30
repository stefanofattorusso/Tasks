package ch.protonmail.android.protonmailtest.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import ch.protonmail.android.protonmailtest.main.model.TaskModel
import ch.protonmail.android.protonmailtest.main.upcoming.UpcomingTasksViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.*

class UpcomingTasksViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val viewModel: UpcomingTasksViewModel by lazy { UpcomingTasksViewModel() }

    private val task1 = TaskDomain(
        Date(),
        getTomorrow(),
        "",
        "",
        "1",
        "",
        false
    )

    private val task2 = TaskDomain(
        getYesterday(),
        Date(),
        "",
        "",
        "2",
        "",
        false
    )

    private val list = listOf(task1, task2)

    @Test
    fun `Given a list of tasks, When the list is set, Then sort them and map to model`() {

        viewModel.setData(list)

        assertEquals(1, viewModel.tasks.value?.count())

        val first = viewModel.tasks.value?.get(0)
        assert(first is TaskModel)
        assertEquals("1", first?.id)
    }

    @Test
    fun `When onTaskClicked is invoked, Then the viewInteraction is the correct`() {

        viewModel.onTaskClicked(TaskModel("", "", "", "", "", "1", false))

        assert(viewModel.viewInteraction.value is UpcomingTasksViewModel.ViewInteraction.TaskClicked)
    }

    private fun getTomorrow(): Date = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 1) }.time

    private fun getYesterday(): Date = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -1) }.time
}