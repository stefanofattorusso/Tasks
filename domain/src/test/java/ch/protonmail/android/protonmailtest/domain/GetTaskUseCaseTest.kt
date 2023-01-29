package ch.protonmail.android.protonmailtest.domain

import ch.protonmail.android.protonmailtest.domain.repository.TaskRepository
import ch.protonmail.android.protonmailtest.domain.usecase.GetTaskUseCase
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class GetTaskUseCaseTest {

    private val repository: TaskRepository = mockk(relaxed = true)

    private val useCase: GetTaskUseCase by lazy { GetTaskUseCase(repository) }

    @Test
    fun `When getTask is called, Then verify that the repository method is invoked`() {

        useCase.getTask("1")

        verify { repository.getTask(any()) }
    }
}