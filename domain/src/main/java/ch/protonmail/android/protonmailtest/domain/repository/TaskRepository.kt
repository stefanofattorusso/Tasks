package ch.protonmail.android.protonmailtest.domain.repository

import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTasks(): Flow<List<TaskDomain>>
}