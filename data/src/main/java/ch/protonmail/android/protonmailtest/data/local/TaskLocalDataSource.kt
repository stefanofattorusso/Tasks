package ch.protonmail.android.protonmailtest.data.local

import ch.protonmail.android.protonmailtest.data.local.dao.TaskDao
import ch.protonmail.android.protonmailtest.data.local.entity.TaskEntity
import ch.protonmail.android.protonmailtest.data.remote.model.Task
import ch.protonmail.android.protonmailtest.data.remote.model.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskLocalDataSource @Inject constructor(
    private val taskDao: TaskDao
) {

    fun getTasks(): Flow<List<TaskEntity>> {
        return taskDao.getAll()
    }

    suspend fun saveTasks(list: List<Task>) {
        taskDao.insertAll(list.map { it.toEntity() })
    }
}
