package ch.protonmail.android.protonmailtest.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import ch.protonmail.android.protonmailtest.data.local.dao.TaskDao
import ch.protonmail.android.protonmailtest.data.local.database.TaskDatabase
import ch.protonmail.android.protonmailtest.data.local.entity.TaskEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class TaskDaoTest {

    private lateinit var taskDao: TaskDao
    private lateinit var db: TaskDatabase

    private val entity = TaskEntity(
        "2",
        "2023-12-23'T'18:20:24",
        "2023-12-23'T'18:20:24",
        "encryptedDescription",
        "encryptedTitle",
        "",
        false
    )

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            TaskDatabase::class.java
        ).build()
        taskDao = db.taskDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertTask() = runTest {

        taskDao.insertAll(listOf(entity))

        val result = taskDao.getAll().first()

        assertEquals("2", result.first().id)
    }

    @Test
    fun updateTaskWithDownloadedImage() = runTest {

        taskDao.insertAll(listOf(entity))

        taskDao.updateTaskWithImageDownloaded("2")

        val result = taskDao.getAll().first()

        assertTrue(result.first().imageDownloaded)
    }
}
