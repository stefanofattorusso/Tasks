package ch.protonmail.android.protonmailtest.main.model

import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import java.text.SimpleDateFormat
import java.util.*

data class TaskModel(
    val creationDate: String,
    val dueDate: String,
    val description: String,
    val title: String,
    val image: String,
    val id: String,
)

fun TaskDomain.toModel(): TaskModel {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd • HH:mm", Locale.getDefault())
    return TaskModel(
        creationDate = dateFormatter.format(creationDate),
        dueDate = dateFormatter.format(dueDate),
        description = description,
        title = title,
        image = image,
        id = id,
    )
}