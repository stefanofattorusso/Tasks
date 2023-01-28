package ch.protonmail.android.protonmailtest.domain.usecase

import ch.protonmail.android.protonmailtest.common.ResultData
import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import ch.protonmail.android.protonmailtest.domain.repository.TaskRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend fun getTasks(): ResultData<List<TaskDomain>> {
        return try {
            ResultData.Success(repository.getTasks())
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }
}
