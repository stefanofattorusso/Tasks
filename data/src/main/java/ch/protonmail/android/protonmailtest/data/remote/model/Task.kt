package ch.protonmail.android.protonmailtest.data.remote.model

import ch.protonmail.android.crypto.CryptoLib
import ch.protonmail.android.protonmailtest.commonandroid.encrypt
import ch.protonmail.android.protonmailtest.data.local.entity.TaskEntity
import com.google.gson.annotations.SerializedName
import java.util.*

data class Task(
    @SerializedName("creation_date")
    val creationDate: String?,
    @SerializedName("due_date")
    val dueDate: String?,
    @SerializedName("encrypted_description")
    val encryptedDescription: String?,
    @SerializedName("encrypted_title")
    val encryptedTitle: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: String?
)

fun Task.toEntity(cryptoLib: CryptoLib): TaskEntity {
    return TaskEntity(
        id = id ?: "",
        creationDate = creationDate.encrypt(cryptoLib),
        dueDate = dueDate.encrypt(cryptoLib),
        encryptedDescription = encryptedDescription ?: "",
        encryptedTitle = encryptedTitle ?: "",
        image = image.encrypt(cryptoLib),
        imageDownloaded = false
    )
}
