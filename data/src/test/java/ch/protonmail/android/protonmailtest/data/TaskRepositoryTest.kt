package ch.protonmail.android.protonmailtest.data

import ch.protonmail.android.crypto.CryptoLib
import ch.protonmail.android.protonmailtest.data.local.TaskLocalDataSource
import ch.protonmail.android.protonmailtest.data.remote.TaskNetworkDataSource
import ch.protonmail.android.protonmailtest.data.repository.TaskRepositoryImpl
import ch.protonmail.android.protonmailtest.domain.repository.TaskRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskRepositoryTest {

    private val dispatcher = StandardTestDispatcher(TestCoroutineScheduler())
    private val networkSource: TaskNetworkDataSource = mockk(relaxed = true)
    private val localSource: TaskLocalDataSource = mockk(relaxed = true)
    private val cryptoLib: CryptoLib = mockk(relaxed = true)

    private val repository: TaskRepository by lazy {
        TaskRepositoryImpl(
            dispatcher,
            networkSource,
            localSource,
            cryptoLib
        )
    }

    @Test
    fun `When getTasks is called, Then retrieve the tasks from the db and launch a network call`() =
        runTest(dispatcher.scheduler) {

            repository.getTasks().onStart {
                coVerify { networkSource.getTasks() }
                coVerify { localSource.saveTasks(any()) }
            }

            coVerify { localSource.getTasks() }
        }
}