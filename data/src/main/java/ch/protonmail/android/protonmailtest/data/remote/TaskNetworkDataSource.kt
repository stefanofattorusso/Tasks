package ch.protonmail.android.protonmailtest.data.remote

import ch.protonmail.android.protonmailtest.data.remote.model.Task
import ch.protonmail.android.protonmailtest.data.remote.network.TaskService
import javax.inject.Inject

class TaskNetworkDataSource @Inject constructor(
    private val service: TaskService
) {

    suspend fun getTasks(): List<Task> {
        return service.getTasks()
    }
}
