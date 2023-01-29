package ch.protonmail.android.protonmailtest.domain

import ch.protonmail.android.protonmailtest.domain.repository.TaskRepository
import ch.protonmail.android.protonmailtest.domain.usecase.SetImageDownloadedUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SetImageDownloadedUseCaseTest {

    private val repository: TaskRepository = mockk(relaxed = true)

    private val useCase: SetImageDownloadedUseCase by lazy { SetImageDownloadedUseCase(repository) }

    @Test
    fun `When getTasks is called, Then verify that the repository method is invoked`() = runBlockingTest {

        useCase.setImageDownloaded("1")

        coVerify { repository.setImageDownloaded(any()) }
    }
}