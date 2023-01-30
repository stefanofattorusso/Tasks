package ch.protonmail.android.protonmailtest.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.protonmail.android.protonmailtest.domain.usecase.GetTaskUseCase
import ch.protonmail.android.protonmailtest.domain.usecase.SetImageDownloadedUseCase
import ch.protonmail.android.protonmailtest.main.model.TaskModel
import ch.protonmail.android.protonmailtest.main.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTaskUseCase: GetTaskUseCase,
    private val setImageDownloadedUseCase: SetImageDownloadedUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    private val taskId: String by lazy { savedStateHandle.get<String>(TASK_ID) ?: "" }

    fun onDownloadImageClicked() {
        viewModelScope.launch {
            setImageDownloadedUseCase.setImageDownloaded(taskId)
        }
    }

    init {
        retrieveTask(taskId)
    }

    private fun retrieveTask(id: String) {
        viewModelScope.launch {
            getTaskUseCase.getTask(id)
                .map { domain -> domain.toModel() }
                .flowOn(Dispatchers.Default)
                .catch { _viewState.value = ViewState.Error }
                .collect { task -> _viewState.value = ViewState.Success(task) }
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Success(val task: TaskModel) : ViewState()
        object Error : ViewState()
    }

    companion object {
        const val TASK_ID = "ID"
    }
}
