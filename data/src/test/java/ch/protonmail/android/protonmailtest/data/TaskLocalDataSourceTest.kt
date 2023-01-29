package ch.protonmail.android.protonmailtest.data

import ch.protonmail.android.protonmailtest.data.local.TaskLocalDataSource
import ch.protonmail.android.protonmailtest.data.local.dao.TaskDao
import ch.protonmail.android.protonmailtest.data.remote.model.Task
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskLocalDataSourceTest {

    private val dispatcher = StandardTestDispatcher(TestCoroutineScheduler())
    private val taskDao: TaskDao = mockk(relaxed = true)

    private val dataSource: TaskLocalDataSource by lazy { TaskLocalDataSource(taskDao) }

    @Test
    fun `When getTasks is called, Then call the method on the dao`() = runTest(dispatcher.scheduler) {

        dataSource.getTasks()

        coVerify { taskDao.getAll() }
    }

    @Test
    fun `When saveTasks is called, Then call the method on the dao`() = runTest(dispatcher.scheduler) {

        dataSource.saveTasks(listOf(Task("", "", "", "", "", "")))

        coVerify { taskDao.insertAll(any()) }
    }
}