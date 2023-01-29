package ch.protonmail.android.protonmailtest.domain.usecase

import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import ch.protonmail.android.protonmailtest.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    fun getTasks(): Flow<List<TaskDomain>> {
        return repository.getTasks()
    }
}
