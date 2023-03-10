package ch.protonmail.android.protonmailtest.data.repository

import ch.protonmail.android.crypto.CryptoLib
import ch.protonmail.android.protonmailtest.commonandroid.IoDispatcher
import ch.protonmail.android.protonmailtest.data.local.TaskLocalDataSource
import ch.protonmail.android.protonmailtest.data.local.entity.toDomain
import ch.protonmail.android.protonmailtest.data.remote.TaskNetworkDataSource
import ch.protonmail.android.protonmailtest.data.remote.model.toEntity
import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import ch.protonmail.android.protonmailtest.domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    @IoDispatcher private val io: CoroutineDispatcher = Dispatchers.IO,
    private val networkSource: TaskNetworkDataSource,
    private val localSource: TaskLocalDataSource,
    private val cryptoLib: CryptoLib,
) : TaskRepository {

    override fun getTask(id: String): Flow<TaskDomain> {
        return localSource.getTask(id)
            .map { task -> task.toDomain(cryptoLib) }
            .flowOn(Dispatchers.Default)
    }

    override fun getTasks(): Flow<List<TaskDomain>> {
        return localSource.getTasks()
            .onStart {
                try {
                    val newTasks = networkSource.getTasks()
                    localSource.saveTasks(newTasks.map { task -> task.toEntity(cryptoLib) })
                } catch (e: Exception) {
                    // do nothing
                }
            }
            .distinctUntilChanged()
            .map { list -> list.map { task -> task.toDomain(cryptoLib) } }
            .flowOn(io)
    }

    override suspend fun setImageDownloaded(id: String) {
        withContext(io) {
            localSource.setImageDownloaded(id)
        }
    }
}
