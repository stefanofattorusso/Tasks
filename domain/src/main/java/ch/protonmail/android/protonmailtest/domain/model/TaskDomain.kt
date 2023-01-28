package ch.protonmail.android.protonmailtest.domain.model

import java.util.*

data class TaskDomain(
    val creationDate: Date,
    val dueDate: Date,
    val description: String,
    val title: String,
    val id: String,
    val image: String
)
