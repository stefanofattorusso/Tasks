package ch.protonmail.android.protonmailtest.data.local

import ch.protonmail.android.protonmailtest.data.local.dao.TaskDao
import ch.protonmail.android.protonmailtest.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskLocalDataSource @Inject constructor(
    private val taskDao: TaskDao,
) {

    fun getTasks(): Flow<List<TaskEntity>> {
        return taskDao.getAll()
    }

    suspend fun saveTasks(list: List<TaskEntity>) {
        taskDao.insertAll(list)
    }

    suspend fun setImageDownloaded(id: String) {
        taskDao.updateTaskWithImageDownloaded(id)
    }
}
