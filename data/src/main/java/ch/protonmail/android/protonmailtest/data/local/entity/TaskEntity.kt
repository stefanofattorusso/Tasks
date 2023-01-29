package ch.protonmail.android.protonmailtest.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val creationDate: String,
    @ColumnInfo val dueDate: String,
    @ColumnInfo val encryptedDescription: String,
    @ColumnInfo val encryptedTitle: String,
    @ColumnInfo val image: String,
)

fun TaskEntity.toDomain(): TaskDomain {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    return TaskDomain(
        creationDate = dateFormatter.parse(creationDate) ?: Date(),
        dueDate = dateFormatter.parse(dueDate) ?: Date(),
        description = encryptedDescription,
        title = encryptedTitle,
        id = id,
        image = image
    )
}
