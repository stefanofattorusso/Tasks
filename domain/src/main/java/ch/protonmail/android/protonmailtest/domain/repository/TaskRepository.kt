package ch.protonmail.android.protonmailtest.domain.repository

import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTask(id: String): Flow<TaskDomain>

    fun getTasks(): Flow<List<TaskDomain>>

    suspend fun setImageDownloaded(id: String)
}