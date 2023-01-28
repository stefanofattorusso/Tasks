package ch.protonmail.android.protonmailtest.data.remote.model

import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("creation_date")
    val creationDate: String,
    @SerializedName("due_date")
    val dueDate: String,
    @SerializedName("encrypted_description")
    val encryptedDescription: String,
    @SerializedName("encrypted_title")
    val encryptedTitle: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String
)

fun Task.toDomain(): TaskDomain {
    return TaskDomain()
}
