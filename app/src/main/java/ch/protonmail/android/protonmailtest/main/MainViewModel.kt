package ch.protonmail.android.protonmailtest.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import ch.protonmail.android.protonmailtest.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    fun fetchTasks() {
        viewModelScope.launch {
            getTasksUseCase.getTasks()
                .catch { _viewState.value = ViewState.Error }
                .collect { _viewState.value = ViewState.Success(it) }
        }
    }

    sealed class ViewState {
        data class Success(val data: List<TaskDomain>) : ViewState()
        object Error : ViewState()
        object Loading : ViewState()
    }
}