package ch.protonmail.android.protonmailtest.domain.repository

import ch.protonmail.android.protonmailtest.domain.model.TaskDomain

interface TaskRepository {

    suspend fun getTasks(): List<TaskDomain>
}