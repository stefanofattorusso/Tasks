package ch.protonmail.android.protonmailtest.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ch.protonmail.android.protonmailtest.data.local.dao.TaskDao
import ch.protonmail.android.protonmailtest.data.local.entity.TaskEntity

@Database( entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao
}