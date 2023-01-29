package ch.protonmail.android.protonmailtest.data

import ch.protonmail.android.protonmailtest.data.remote.TaskNetworkDataSource
import ch.protonmail.android.protonmailtest.data.remote.network.TaskService
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskNetworkDataSourceTest {

    private val dispatcher = StandardTestDispatcher(TestCoroutineScheduler())
    private val service: TaskService = mockk(relaxed = true)

    private val dataSource: TaskNetworkDataSource by lazy { TaskNetworkDataSource(service) }

    @Test
    fun `When getTasks is called, Then call the method on the dao`() = runTest(dispatcher.scheduler) {

        dataSource.getTasks()

        coVerify { service.getTasks() }
    }
}