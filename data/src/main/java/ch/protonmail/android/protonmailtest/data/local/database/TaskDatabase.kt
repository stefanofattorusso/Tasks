package ch.protonmail.android.protonmailtest.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ch.protonmail.android.protonmailtest.data.local.dao.TaskDao
import ch.protonmail.android.protonmailtest.data.local.entity.TaskEntity

@Database( entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {

        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(
            context: Context,
        ): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): TaskDatabase {
            return Room.databaseBuilder(
                context,
                TaskDatabase::class.java,
                "task-database"
            ).fallbackToDestructiveMigration().build()
        }
    }
}