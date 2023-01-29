package ch.protonmail.android.protonmailtest.domain

import ch.protonmail.android.protonmailtest.domain.repository.TaskRepository
import ch.protonmail.android.protonmailtest.domain.usecase.GetTasksUseCase
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class GetTasksUseCaseTest {

    private val repository: TaskRepository = mockk(relaxed = true)

    private val useCase: GetTasksUseCase by lazy { GetTasksUseCase(repository) }

    @Test
    fun `When getTasks is called, Then verify that the repository method is invoked`() {

        useCase.getTasks()

        verify { repository.getTasks() }
    }
}