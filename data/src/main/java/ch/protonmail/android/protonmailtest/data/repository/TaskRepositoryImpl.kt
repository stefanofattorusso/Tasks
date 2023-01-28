package ch.protonmail.android.protonmailtest.data.repository

import ch.protonmail.android.protonmailtest.commonandroid.IoDispatcher
import ch.protonmail.android.protonmailtest.data.remote.TaskDataSource
import ch.protonmail.android.protonmailtest.data.remote.model.toDomain
import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import ch.protonmail.android.protonmailtest.domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val source: TaskDataSource
) : TaskRepository {

    override suspend fun getTasks(): List<TaskDomain> {
        return withContext(context = ioDispatcher) {
            source.getTasks().map { task -> task.toDomain() }
        }
    }
}
