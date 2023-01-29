package ch.protonmail.android.protonmailtest.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ch.protonmail.android.crypto.CryptoLib
import ch.protonmail.android.protonmailtest.commonandroid.decrypt
import ch.protonmail.android.protonmailtest.commonandroid.parseTaskDate
import ch.protonmail.android.protonmailtest.domain.model.TaskDomain

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val creationDate: String,
    @ColumnInfo val dueDate: String,
    @ColumnInfo val encryptedDescription: String,
    @ColumnInfo val encryptedTitle: String,
    @ColumnInfo val image: String,
    @ColumnInfo val imageDownloaded: Boolean,
)

fun TaskEntity.toDomain(cryptoLib: CryptoLib): TaskDomain {
    return TaskDomain(
        id = id,
        creationDate = creationDate.decrypt(cryptoLib).parseTaskDate(),
        dueDate = dueDate.decrypt(cryptoLib).parseTaskDate(),
        description = encryptedDescription.decrypt(cryptoLib),
        title = encryptedTitle.decrypt(cryptoLib),
        image = image.decrypt(cryptoLib),
        imageDownloaded = imageDownloaded
    )
}
