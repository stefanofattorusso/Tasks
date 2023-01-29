package ch.protonmail.android.protonmailtest.domain.usecase

import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import ch.protonmail.android.protonmailtest.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    fun getTask(id: String): Flow<TaskDomain> {
        return repository.getTask(id)
    }
}
