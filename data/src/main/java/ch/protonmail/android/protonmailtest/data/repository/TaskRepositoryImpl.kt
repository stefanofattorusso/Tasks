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
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val networkSource: TaskNetworkDataSource,
    private val localSource: TaskLocalDataSource,
    private val cryptoLib: CryptoLib,
) : TaskRepository {

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
            .flowOn(ioDispatcher)
    }
}
