package ch.protonmail.android.protonmailtest.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.protonmail.android.protonmailtest.domain.model.TaskDomain
import ch.protonmail.android.protonmailtest.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    fun fetchTasks() {
        viewModelScope.launch {
            getTasksUseCase.getTasks()
                .catch { _viewState.postValue(ViewState.Error) }
                .collect { _viewState.postValue(ViewState.Success(it)) }
        }
    }

    sealed class ViewState {
        data class Success(val data: List<TaskDomain>) : ViewState()
        object Error : ViewState()
    }
}