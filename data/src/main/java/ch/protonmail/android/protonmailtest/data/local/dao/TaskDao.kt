package ch.protonmail.android.protonmailtest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ch.protonmail.android.protonmailtest.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE id = :id")
    fun get(id: String): Flow<TaskEntity>

    @Query("SELECT * FROM task")
    fun getAll(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(monthEntities: List<TaskEntity>)

    @Query("UPDATE task SET imageDownloaded = 1 WHERE id = :id")
    suspend fun updateTaskWithImageDownloaded(id: String)
}
