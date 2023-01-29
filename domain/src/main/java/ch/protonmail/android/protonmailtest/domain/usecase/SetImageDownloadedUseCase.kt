package ch.protonmail.android.protonmailtest.domain.usecase

import ch.protonmail.android.protonmailtest.domain.repository.TaskRepository
import javax.inject.Inject

class SetImageDownloadedUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend fun setImageDownloaded(id: String) {
        repository.setImageDownloaded(id)
    }
}